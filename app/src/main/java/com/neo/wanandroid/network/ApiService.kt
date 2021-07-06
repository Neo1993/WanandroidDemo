package com.neo.wanandroid.network

import com.neo.wanandroid.model.bean.ApiPageResponse
import com.neo.wanandroid.model.bean.BannerResponse
import com.neo.wanandroid.model.bean.HomeArticle
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    companion object {
        const val BASE_URL = "https://www.wanandroid.com"
    }

    /**
     * 获取首页轮播图
     */
    @GET("/banner/json")
    suspend fun getBanner() : ApiResponse<List<BannerResponse>>

    /**
     * 获取首页文章
     */
    @GET("/article/list/{path}/json")
    suspend fun getHomeArticleList(@Path("page") page: Int) : ApiResponse<ApiPageResponse<List<HomeArticle>>>

}