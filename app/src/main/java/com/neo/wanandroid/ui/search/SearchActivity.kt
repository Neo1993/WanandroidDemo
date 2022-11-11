package com.neo.wanandroid.ui.search

import android.os.Bundle
import android.view.Menu
import android.view.View
import android.view.Window
import androidx.appcompat.widget.SearchView
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.neo.wanandroid.R
import com.neo.wanandroid.base.BaseActivity
import com.neo.wanandroid.databinding.ActivitySearchBinding
import com.neo.wanandroid.ext.init
import com.neo.wanandroid.ext.initClose
import com.neo.wanandroid.ext.showMessage
import com.neo.wanandroid.ui.adapter.HotSearchAdapter
import com.neo.wanandroid.ui.adapter.RecentSearchAdapter
import com.neo.wanandroid.utils.CacheUtil
import com.neo.wanandroid.vm.SearchVM

class SearchActivity: BaseActivity<SearchVM, ActivitySearchBinding>() {
    private val hotSearchAdapter by lazy { HotSearchAdapter() }
    private val recentSearchAdapter by lazy { RecentSearchAdapter() }

    override fun initView(savedInstanceState: Bundle?) {
        mDataBinding.vm = mViewModel;
        mDataBinding.viewClick = this
        setMenu()
        val flexboxLayoutManager1 = FlexboxLayoutManager(this, FlexDirection.ROW, FlexWrap.WRAP)
        mDataBinding.hotRV.init(flexboxLayoutManager1, hotSearchAdapter)
        hotSearchAdapter.run {
            setOnItemClickListener { adapter, view, position ->
                val key = hotSearchAdapter.data.get(position).name
                updateKey(key)
            }
        }

        val flexboxLayoutManager2 = FlexboxLayoutManager(this, FlexDirection.ROW, FlexWrap.WRAP)
        mDataBinding.recentRV.init(flexboxLayoutManager2, recentSearchAdapter)
        recentSearchAdapter.run {
            setOnItemChildClickListener { adapter, view, position ->
                when(view.id){
                    R.id.deleteIV -> {
                        val key = recentSearchAdapter.data.get(position)
                        deleteKey(key)
                    }
                }
            }
        }
        initLoadData()
    }

    override fun initData() {
        mViewModel.run {
            searchKeyList.observe(this@SearchActivity){
                hotSearchAdapter.setList(it)
            }

            historyKeyList.observe(this@SearchActivity){
                recentSearchAdapter.setList(it)
            }
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_search
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val searchView = menu?.findItem(R.id.action_search)?.actionView as SearchView
        searchView.run {
            queryHint = "输入关键字搜索"
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    query.let {
                        if(it.isNullOrEmpty()) showMessage("搜索词不能为空哦~~") else updateKey(it)
                    }
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }

            } )
        }
        return super.onPrepareOptionsMenu(menu)
    }

    private fun setMenu() {
        setSupportActionBar(mDataBinding.includeToolbar.toolbar)
        supportActionBar?.run {
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }
        mDataBinding.includeToolbar.toolbar.run {
            initClose {
                finish()
            }
        }

        window.invalidatePanelMenu(Window.FEATURE_OPTIONS_PANEL)
        invalidateOptionsMenu()
    }

    /**
     * 更新搜索词
     */
    private fun updateKey(key: String){
        mViewModel.historyKeyList.value?.let {
            if(it.contains(key)){
                //当搜索历史中包含该数据时 删除
                it.remove(key)
            }else if(it.size >= 10){
                //如果集合的size 有10个以上了，删除最后一个
                it.removeAt(it.size - 1)
            }
            it.add(0, key)
            CacheUtil.setSearchHistory(it)
            mViewModel.historyKeyList.value = it
        }
    }

    /**
     * 删除搜索词
     */
    private fun deleteKey(key: String){
        mViewModel.historyKeyList.value?.let {
            if(it.contains(key)){
                //当搜索历史中包含该数据时 删除
                it.remove(key)
            }
            CacheUtil.setSearchHistory(it)
            mViewModel.historyKeyList.value = it
        }
    }

    /**
     * 删除所有搜索词
     */
    private fun deleteAllKey(){
        CacheUtil.deleteAllSearchHistory()
    }

    /**
     * 点击删除搜索词
     */
    fun clickDeleteKey() {
        mDataBinding.run {
            deleteTV.visibility = View.GONE
            cancelTV.visibility = View.VISIBLE
            delAllTV.visibility = View.VISIBLE
        }
        recentSearchAdapter.setEditState(true)
    }

    /**
     * 点击取消删除搜索词
     */
    fun clickCancelDelete() {
        mDataBinding.run {
            deleteTV.visibility = View.VISIBLE
            cancelTV.visibility = View.GONE
            delAllTV.visibility = View.GONE
        }
        recentSearchAdapter.setEditState(false)
    }

    /**
     * 点击删除所有搜索词
     */
    fun deleteAll() {
        mDataBinding.run {
            deleteTV.visibility = View.VISIBLE
            cancelTV.visibility = View.GONE
            delAllTV.visibility = View.GONE
        }
        deleteAllKey()
        recentSearchAdapter.data.clear()
        recentSearchAdapter.setEditState(false)
    }

    private fun initLoadData() {
        mViewModel.getSearchData()
        mViewModel.getHistoryData()
    }

}