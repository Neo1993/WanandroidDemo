package com.neo.wanandroid.vm

import androidx.lifecycle.MutableLiveData
import com.neo.wanandroid.base.BaseViewModel
import com.neo.wanandroid.ext.request
import com.neo.wanandroid.model.bean.ApiPageResponse
import com.neo.wanandroid.model.bean.ArticleResponse
import com.neo.wanandroid.model.bean.BannerResponse
import com.neo.wanandroid.network.RequestCallback
import com.neo.wanandroid.network.apiService

class RequestHomeViewModel : BaseViewModel() {
    val bannerData: MutableLiveData<RequestCallback<List<BannerResponse>>> = MutableLiveData()
    val articleList: MutableLiveData<RequestCallback<ApiPageResponse<ArrayList<ArticleResponse>>>> = MutableLiveData()

    private var page : Int = 0;
    private var isRefresh : Boolean = true;

    fun getBanner() {
        request({ apiService.getBanner() }, bannerData)
    }

    fun getHomeArticle() {
        if (isRefresh) page = 0 else page++
        request({ apiService.getHomeArticleList(page) }, articleList)
    }


}