package com.neo.wanandroid.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.drake.brv.BindingAdapter
import com.drake.brv.annotaion.AnimationType
import com.drake.brv.utils.divider
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import com.drake.net.Get
import com.drake.net.cache.CacheMode
import com.drake.net.utils.scope
import com.neo.wanandroid.R
import com.neo.wanandroid.app.constant.Api
import com.neo.wanandroid.base.BaseFragment
import com.neo.wanandroid.databinding.FragmentHomeBinding
import com.neo.wanandroid.ext.initFloatBT
import com.neo.wanandroid.model.bean.ApiPageResponse
import com.neo.wanandroid.model.bean.ArticleResponse
import com.neo.wanandroid.model.bean.BannerResponse
import com.neo.wanandroid.ui.search.SearchActivity
import com.neo.wanandroid.vm.HomeVM
import com.neo.wanandroid.vm.RequestCollectVM

class HomeFragment : BaseFragment<HomeVM, FragmentHomeBinding>() {
//    private val articleAdapter: ArticleAdapter = ArticleAdapter(arrayListOf())
    private val requestCollectVM: RequestCollectVM = RequestCollectVM()
    private lateinit var bindingAdapter: BindingAdapter

    override fun initView(savedInstanceState: Bundle?) {

        //初始化 toolbar
        mDataBinding.includeToolbar.toolbar.run {
            title = "首页"
            inflateMenu(R.menu.home_menu)
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.home_search -> {
                        startActivity(Intent(mActivity, SearchActivity::class.java))
                    }
                }
                true
            }
        }

        bindingAdapter = mDataBinding.recyclerView.linear().divider {
            setDivider(10, true)
            includeVisible = true
        }.setup {
            setAnimation(AnimationType.SCALE)
            addType<BannerResponse>(R.layout.include_banner)
            addType<ArticleResponse>{
                if(envelopePic.isNullOrEmpty()) R.layout.item_adapter_article else R.layout.item_adapter_project
            }

            R.id.item.onClick {
                val data = getModel<ArticleResponse>()
                data?.let {
                    findNavController().navigate(R.id.action_to_webFragment, Bundle().apply {
                        putString("showTitle", it.title)
                        putString("url", it.link)
                    })
                }
            }
        }

        mDataBinding.recyclerView.initFloatBT(mDataBinding.floatBT)

    }

    override fun initData() {
        mDataBinding.refreshLayout.onRefresh {
            scope{
                val topDatas = Get<ArrayList<ArticleResponse>>(Api.TOP_ARTICLE) {
                    setCacheMode(CacheMode.READ_THEN_REQUEST)
                }
                val datas = Get<ApiPageResponse<ArrayList<ArticleResponse>>>(
                    String.format(
                        Api.ARTICLE,
                        index
                    )
                ) {
                    setCacheMode(CacheMode.READ_THEN_REQUEST)
                }
                val models = mutableListOf<ArticleResponse>()
                try {
                    models.addAll(topDatas.await())
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                models.addAll(datas.await().datas)
                bindingAdapter.models = models
            }
        }.autoRefresh()

        mDataBinding.refreshLayout.onLoadMore {
            scope {
                bindingAdapter.addModels(
                    Get<ApiPageResponse<ArrayList<ArticleResponse>>>(
                        String.format(
                            Api.ARTICLE,
                            index
                        )
                    ).await().datas
                )
            }
        }
    }

}