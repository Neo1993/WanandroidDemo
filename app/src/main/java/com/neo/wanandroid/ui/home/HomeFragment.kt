package com.neo.wanandroid.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.ConvertUtils
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.neo.wanandroid.R
import com.neo.wanandroid.app.appVM
import com.neo.wanandroid.app.constant.PATH_ACTIVITY_COMMONWEB
import com.neo.wanandroid.app.eventVM
import com.neo.wanandroid.base.BaseVMFragment
import com.neo.wanandroid.ext.*
import com.neo.wanandroid.model.bean.BannerResponse
import com.neo.wanandroid.ui.CommonWebActivity
import com.neo.wanandroid.ui.adapter.ArticleAdapter
import com.neo.wanandroid.ui.widget.recyclerview.SpaceItemDecoration
import com.neo.wanandroid.vm.HomeVM
import com.neo.wanandroid.vm.RequestCollectVM
import com.zhpan.bannerview.BannerViewPager
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseVMFragment<HomeVM>() {
    private val articleAdapter: ArticleAdapter = ArticleAdapter(arrayListOf())
    private lateinit var loadService: LoadService<Any>
    private val requestCollectVM: RequestCollectVM = RequestCollectVM()

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

        //初始化SmartRefreshLayout的下拉刷新和上拉加载
        refreshLayout.init ({
            initLoadData()
        },{
            mViewModel.getHomeArticle(false)
        })

        //初始化RecyclerView
        recyclerView.init(LinearLayoutManager(context), articleAdapter).let {
            it.addItemDecoration(SpaceItemDecoration(0, ConvertUtils.dp2px(8f), false))
        }

        articleAdapter.apply {
            setCollectClick { item, v, position ->
                if(v.isChecked){
                    requestCollectVM.uncollect(item.id)
                }else{
                    requestCollectVM.collect(item.id)
                }
            }

            setOnItemClickListener { adapter, view, position ->
                val data = articleAdapter.data.get(position - this@HomeFragment.recyclerView.headerCount)
                data.run {
                    ARouter.getInstance().build(PATH_ACTIVITY_COMMONWEB)
                        .withInt("id", id)
                        .withString("title", title)
                        .withString("url", link)
                        .withBoolean("isCollect", collect)
                        .navigation()
                }
            }
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

        requestCollectVM.apply {
            collectState.observe(this@HomeFragment){
                if(!it.isSuccess){
                    showMessage(it.errorMsg)
                }
            }
        }

//        eventVM.apply {
//            collectState.observeInFragment(this@HomeFragment){
//                collectState.value?.let {
//                    if(it.isSuccess){
//                        run breaking@{
//                            articleAdapter.data.indices.forEach { index ->
//                                val data = articleAdapter.data.get(index)
//                                if(it.id == data.id){
//                                    data.collect = it.isCollect
//                                    articleAdapter.notifyItemChanged(index)
//                                    return@breaking
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }

        appVM.apply {
            currentUser.observe(this@HomeFragment, {
                if(it != null){     //已登录
                    it.collectIds.forEach { id ->
                        for(item in articleAdapter.data){
                            if(id.toInt() ==  item.id){
                                item.collect = true
                                break
                            }
                        }
                    }
                }else{              //未登录
                    for(item in articleAdapter.data){
                        item.collect = false
                    }
                }
            })
            articleAdapter.notifyDataSetChanged()
        }

        //监听全局的收藏信息 收藏的Id跟本列表的数据id匹配则需要更新
        eventVM.apply {
            collectState.observeInFragment(this@HomeFragment){
                for (index in articleAdapter.data.indices){
                    val item = articleAdapter.data.get(index)
                    if(it.id == item.id){
                        item.collect = it.isCollect
                        articleAdapter.notifyItemChanged(index)
                    }
                }
            }
        }

    }

    fun initLoadData() {
        mViewModel.getBanner()
        mViewModel.getHomeArticle(true)
    }

}