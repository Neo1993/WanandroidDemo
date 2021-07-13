package com.neo.wanandroid.vm

import androidx.lifecycle.MutableLiveData
import com.neo.wanandroid.base.BaseViewModel
import com.neo.wanandroid.ext.request
import com.neo.wanandroid.model.bean.ApiPageResponse
import com.neo.wanandroid.model.bean.ArticleResponse
import com.neo.wanandroid.model.bean.BannerResponse
import com.neo.wanandroid.model.repository.request.HttpRequestManager
import com.neo.wanandroid.model.repository.request.httpRequstManager
import com.neo.wanandroid.network.RequestCallback
import com.neo.wanandroid.network.apiService
import com.neo.wanandroid.utils.LogUtils

class RequestHomeViewModel : BaseViewModel() {
    val bannerData: MutableLiveData<RequestCallback<List<BannerResponse>>> = MutableLiveData()
//    val articleList: MutableLiveData<RequestCallback<ApiPageResponse<ArrayList<ArticleResponse>>>> = MutableLiveData()
    val articleList : MutableLiveData<ApiPageResponse<ArrayList<ArticleResponse>>> = MutableLiveData()

    var page : Int = 0;

    fun getBanner() {
        request({ apiService.getBanner() }, bannerData)
    }

//    fun getHomeArticle(isRefresh: Boolean = false) {
//        if (isRefresh) page = 0 else page++
//        request({ httpRequstManager.getHomeArticleList(page) }, articleList)
//    }

    fun getHomeArticle(isRefresh: Boolean = false) {
        if (isRefresh) page = 0
        request({ httpRequstManager.getHomeArticleList(page) }, {
            articleList.postValue(it)
            page++
        },
            {
                LogUtils.debugInfo(it.message)
            })
    }



}