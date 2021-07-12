package com.neo.wanandroid.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.blankj.utilcode.util.ConvertUtils
import com.neo.wanandroid.R
import com.neo.wanandroid.base.BaseFragment
import com.neo.wanandroid.base.BaseVMFragment
import com.neo.wanandroid.ext.handleCallback
import com.neo.wanandroid.ext.init
import com.neo.wanandroid.model.bean.ArticleResponse
import com.neo.wanandroid.model.bean.BannerResponse
import com.neo.wanandroid.ui.adapter.ArticleAdapter
import com.neo.wanandroid.ui.widget.recyclerview.SpaceItemDecoration
import com.neo.wanandroid.vm.RequestHomeViewModel
import com.zhpan.bannerview.BannerViewPager
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.include_banner.view.*
import java.util.ArrayList

class HomeFragment : BaseVMFragment<RequestHomeViewModel>() {
    private val articleAdapter: ArticleAdapter = ArticleAdapter(arrayListOf())

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun initView(savedInstanceState: Bundle?) {
        recyclerView.init(LinearLayoutManager(context), articleAdapter).let {
            it.addItemDecoration(SpaceItemDecoration(0,ConvertUtils.dp2px(8f), false))
        }
        mViewModel.getBanner()
        mViewModel.getHomeArticle()
        createObserver()
    }

    fun createObserver() {
        mViewModel.run {
            //监听轮播图请求的数据变化
            bannerData.observe(viewLifecycleOwner, Observer {
                handleCallback(it, {dataList ->
                    if(recyclerView.headerCount == 0){
                        val headView = LayoutInflater.from(context).inflate(R.layout.include_banner,null).apply{
                            findViewById<BannerViewPager<BannerResponse, HomeBannerViewHolder>>(R.id.bannerVP).apply {
                                adapter = HomeBannerAdapter()
                                setLifecycleRegistry(lifecycle)
                                setOnPageClickListener {
                                    Toast.makeText(context, "positon==$it", Toast.LENGTH_SHORT).show()
                                }
                                create(dataList)
                            }
                        }
                        recyclerView.addHeaderView(headView)
//                        recyclerView.smoothScrollToPosition(0)
                    }
                })
            })
            //监听文章数据列表的变化
            articleList.observe(viewLifecycleOwner, Observer {
                handleCallback(it, {
                    articleAdapter.addData(it.datas)
                })

            })
        }
    }

}