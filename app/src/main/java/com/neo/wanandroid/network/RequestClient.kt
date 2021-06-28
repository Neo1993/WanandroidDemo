package com.neo.wanandroid.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * 创建单例对象 -- 双重校验锁
 */
val apiService: ApiService by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
    RequestClient.INSTANCE.getApi(ApiService::class.java, ApiService.BASE_URL)
}


class RequestClient {

    companion object {
        //创建单例对象
        val INSTANCE: RequestClient by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            RequestClient()
        }
    }

    /**
     * 配置OkhttpClient
     */
    private fun createOkHttpClientBuilder(): OkHttpClient.Builder {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val builder = OkHttpClient.Builder()
        return builder.connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)

    }

    /**
     * 配置Retrofit
     */
    private fun createRetrofitBuilder(): Retrofit.Builder {
        return Retrofit.Builder().client(createOkHttpClientBuilder().build())
                .addConverterFactory(GsonConverterFactory.create())
    }

    /**
     * 根据配置获取请求对象
     */
    fun <T> getApi(serviceClass: Class<T>, baseUrl: String): T {
        return createRetrofitBuilder()
                .client(createOkHttpClientBuilder().build())
                .baseUrl(baseUrl)
                .build()
                .create(serviceClass)
    }

}