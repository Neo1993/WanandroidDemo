package com.neo.wanandroid.vm

import androidx.lifecycle.MutableLiveData
import com.lib.common.base.BaseViewModel
import com.neo.wanandroid.ext.launch
import com.neo.wanandroid.ext.request
import com.neo.wanandroid.model.bean.SearchResponse
import com.neo.wanandroid.network.apiService
import com.neo.wanandroid.utils.CacheUtil

class SearchVM: BaseViewModel() {
    var searchKeyList: MutableLiveData<List<SearchResponse>> = MutableLiveData()
    var historyKeyList: MutableLiveData<ArrayList<String>> = MutableLiveData()

    fun getSearchData(){
        request({apiService.getSearchData()},{
            searchKeyList.value = it
        })
    }

    fun getHistoryData(){
        launch({
               CacheUtil.getSearchHistoryList()
        },{
            historyKeyList.value = it
        })
    }

}