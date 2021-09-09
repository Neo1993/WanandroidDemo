package com.neo.wanandroid.network.interceptor

import com.neo.wanandroid.utils.CacheUtil
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * 自定义头部参数拦截器
 */
class MyHeadInterceptor: Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        builder.addHeader("device", "Android")
        builder.addHeader("isLogin", CacheUtil.isLogin().toString())
        return chain.proceed(builder.build())
    }
}