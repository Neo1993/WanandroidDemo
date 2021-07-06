package com.neo.wanandroid.vm

import android.util.MutableDouble
import androidx.lifecycle.MutableLiveData
import com.neo.wanandroid.base.BaseViewModel
import com.neo.wanandroid.ext.request
import com.neo.wanandroid.model.bean.ApiPageResponse
import com.neo.wanandroid.model.bean.BannerResponse
import com.neo.wanandroid.model.bean.HomeArticle
import com.neo.wanandroid.network.ApiResponse
import com.neo.wanandroid.network.RequestCallback
import com.neo.wanandroid.network.apiService

class RequestHomeViewModel : BaseViewModel() {
    val bannerData: MutableLiveData<RequestCallback<List<BannerResponse>>> = MutableLiveData()
    val articleList: MutableLiveData<RequestCallback<ApiPageResponse<List<HomeArticle>>>> = MutableLiveData()
//    val articleList: MutableLiveData<RequestCallback<List<HomeArticle>>> = MutableLiveData()

    fun getBanner() {
        request({ apiService.getBanner() }, bannerData)
    }

    fun getHomeArticle( ) {
        request(
            {
               apiService.getHomeArticleList(1)
            }, articleList
        )

    }


}