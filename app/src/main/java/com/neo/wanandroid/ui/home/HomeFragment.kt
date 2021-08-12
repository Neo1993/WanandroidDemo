package com.neo.wanandroid.ui.home

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.blankj.utilcode.util.ConvertUtils
import com.blankj.utilcode.util.DeviceUtils
import com.blankj.utilcode.util.ScreenUtils
import com.kingja.loadsir.LoadSirUtil
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.neo.wanandroid.R
import com.neo.wanandroid.base.BaseFragment
import com.neo.wanandroid.base.BaseVMFragment
import com.neo.wanandroid.ext.*
import com.neo.wanandroid.model.bean.ArticleResponse
import com.neo.wanandroid.model.bean.BannerResponse
import com.neo.wanandroid.ui.adapter.ArticleAdapter
import com.neo.wanandroid.ui.widget.loadcallback.LoadingCallback
import com.neo.wanandroid.ui.widget.recyclerview.SpaceItemDecoration
import com.neo.wanandroid.vm.RequestHomeViewModel
import com.yanzhenjie.recyclerview.SwipeMenu
import com.yanzhenjie.recyclerview.SwipeMenuCreator
import com.yanzhenjie.recyclerview.SwipeMenuItem
import com.zhpan.bannerview.BannerViewPager
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.include_banner.view.*
import java.util.ArrayList

class HomeFragment : BaseVMFragment<RequestHomeViewModel>() {
    private val articleAdapter: ArticleAdapter = ArticleAdapter(arrayListOf())
    private lateinit var loadService: LoadService<Any>

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun init(savedInstanceState: Bundle?) {
        //配置请求状态页
        loadService = LoadSir.getDefault().register(mFragmentView) {
            loadService.showLoading()
            initLoadData()
        }
        loadService.showLoading()

        //初始化SwipeRefreshLayout
//        swipeRefreshLayout.init {
//            initLoadData()
//        }
        //初始化SmartRefreshLayout的下拉刷新和上拉加载
        refreshLayout.init ({
            initLoadData()
        },{
            mViewModel.getHomeArticle(false)
        })

//        val swipeMenuCreator = object : SwipeMenuCreator{
//            override fun onCreateMenu(leftMenu: SwipeMenu?, rightMenu: SwipeMenu?, position: Int) {
//                val deleteItem = SwipeMenuItem(context)
//                deleteItem.setBackgroundColor(Color.RED)
//                          .setText("删除")
//                          .setHeight(ViewGroup.LayoutParams.MATCH_PARENT)
//                          .setWidth(200)
//                rightMenu?.addMenuItem(deleteItem)
//            }
//        }
//
//        recyclerView.setSwipeMenuCreator(swipeMenuCreator)

        //初始化RecyclerView
        recyclerView.init(LinearLayoutManager(context), articleAdapter).let {
            it.addItemDecoration(SpaceItemDecoration(0, ConvertUtils.dp2px(8f), false))
        }

        initLoadData()

    }

    override fun createObserver() {
        mViewModel.run {
            //监听轮播图请求的数据变化
            bannerData.observe(viewLifecycleOwner, Observer {
                handleCallback(it, { dataList ->
                    if (recyclerView.headerCount == 0) {
                        val headView = LayoutInflater.from(context).inflate(R.layout.include_banner, null).apply {
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
            articleListState.observe(viewLifecycleOwner, Observer {
                loadService.showSuccess()
//                refreshLayout.setEnableLoadMore(it.hasMore)
                refreshLayout.finishRefresh(it.isSuccess)
                refreshLayout.finishLoadMore(0, it.isSuccess, !it.hasMore)

                if (it.isSuccess) {
                    when {
                        it.isFirstEmpty -> {
                            loadService.showEmpty()
                        }
                        it.isRefresh -> {
                            articleAdapter.setList(it.dataList)
                        }
                        else -> {
                            articleAdapter.addData(it.dataList)
                        }
                    }
                } else {
                    when {
                        it.isRefresh -> {
                            loadService.showError(it.errorMsg)
                        }
                        else -> {
//                            recyclerView.loadMoreError(0, it.errorMsg)
                        }
                    }
                }
            })
        }
    }

    fun initLoadData() {
        mViewModel.getBanner()
        mViewModel.getHomeArticle(true)
    }

}