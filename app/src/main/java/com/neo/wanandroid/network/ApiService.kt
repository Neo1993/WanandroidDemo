package com.neo.wanandroid.network

import com.neo.wanandroid.model.bean.*
import retrofit2.http.*

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

    /**
     * 注册
     */
    @FormUrlEncoded
    @POST("/user/register")
    suspend fun register(@Field("username") username: String, @Field("password") password: String, @Field("repassword") repassword: String) : ApiResponse<Any>

    /**
     * 登录
     */
    @FormUrlEncoded
    @POST("/user/login")
    suspend fun login(@Field("username") username: String, @Field("password") password: String) : ApiResponse<User>

    /**
     * 收藏文章
     */
    @POST("lg/collect/{id}/json")
    suspend fun collect(@Path("id") id: Int): ApiResponse<Any?>

    /**
     * 取消收藏文章
     */
    @POST("lg/uncollect_originId/{id}/json")
    suspend fun uncollect(@Path("id") id: Int): ApiResponse<Any?>

    /**
     * 获取热门搜索数据
     */
    @GET("hotkey/json")
    suspend fun getSearchData(): ApiResponse<List<SearchResponse>>

}