package com.neo.wanandroid.network

import com.neo.wanandroid.model.bean.ApiPageResponse
import com.neo.wanandroid.model.bean.ArticleResponse
import com.neo.wanandroid.model.bean.BannerResponse
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
     * 获取置顶文章列表
     */
    @GET("article/top/json")
    suspend fun getTopArticleList() : ApiResponse<ArrayList<ArticleResponse>>

    /**
     * 获取首页文章
     */
    @GET("/article/list/{page}/json")
    suspend fun getHomeArticleList(@Path("page") page: Int) : ApiResponse<ApiPageResponse<ArrayList<ArticleResponse>>>

}