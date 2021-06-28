package com.neo.wanandroid.vm

import com.neo.wanandroid.base.BaseViewModel
import com.neo.wanandroid.ext.request
import com.neo.wanandroid.model.bean.BannerResponse
import com.neo.wanandroid.network.ApiResponse

class RequestHomeViewModel : BaseViewModel() {

    fun getBanner() {

    }

    suspend fun test() : ApiResponse<BannerResponse>{
        return ApiResponse(0, BannerResponse(), "123")
    }

}