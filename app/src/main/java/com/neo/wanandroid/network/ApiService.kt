package com.neo.wanandroid.network

import retrofit2.http.GET

interface ApiService {
    companion object {
        const val BASE_URL = "https://www.wanandroid.com"
    }

    @GET("/banner/json")
    suspend fun getBanner() : ApiResponse<String>

}