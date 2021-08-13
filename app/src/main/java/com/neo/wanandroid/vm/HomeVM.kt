package com.neo.wanandroid.vm

import androidx.lifecycle.MutableLiveData
import com.neo.wanandroid.base.BaseViewModel
import com.neo.wanandroid.ext.request
import com.neo.wanandroid.model.bean.ApiPageResponse
import com.neo.wanandroid.model.bean.ArticleResponse
import com.neo.wanandroid.model.bean.BannerResponse
import com.neo.wanandroid.model.bean.ListDataUiState
import com.neo.wanandroid.model.repository.request.HttpRequestManager
import com.neo.wanandroid.model.repository.request.httpRequstManager
import com.neo.wanandroid.network.ApiResponse
import com.neo.wanandroid.network.RequestCallback
import com.neo.wanandroid.network.apiService
import com.neo.wanandroid.utils.LogUtils

class HomeVM : BaseViewModel() {
    val bannerData: MutableLiveData<RequestCallback<List<BannerResponse>>> = MutableLiveData()
    val articleListState: MutableLiveData<ListDataUiState<ArticleResponse>> = MutableLiveData()

    var page : Int = 0;

    fun getBanner() {
        request({ apiService.getBanner() }, bannerData)
    }

    fun getHomeArticle(isRefresh: Boolean = false) {
        if (isRefresh) page = 0
        request({ httpRequstManager.getHomeArticleList(page) }, {
            //请求成功
            page++
            val listDataUiState = ListDataUiState(
                    isSuccess = true,
                    isEmpty = it.isEmpty(),
                    isFirstEmpty = isRefresh && it.isEmpty(),
                    isRefresh = isRefresh,
                    hasMore = !it.over,
                    dataList = it.datas
            )
            articleListState.postValue(listDataUiState)
        }, {
            //请求失败
            LogUtils.debugInfo(it.message)
            val listDataUiState = ListDataUiState(
                    isSuccess = false,
                    isRefresh = isRefresh,
                    errorMsg = it.errorMsg,
                    dataList = arrayListOf<ArticleResponse>()
            )
            articleListState.postValue(listDataUiState)
        })
    }



}