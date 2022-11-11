package com.neo.wanandroid.network

import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.neo.wanandroid.app.MainApplication
import com.neo.wanandroid.network.interceptor.CacheInterceptor
import com.neo.wanandroid.network.interceptor.MyHeadInterceptor
import com.neo.wanandroid.network.interceptor.logging.LogInterceptor
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * 创建单例对象
 */
val apiService: ApiService by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
    RequestClient().getApi(ApiService::class.java, ApiService.BASE_URL)
}

val cookieJar: PersistentCookieJar by lazy {
    PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(MainApplication.instance))
}

class RequestClient {
    /**
     * 配置OkhttpClient
     */
    private fun createOkHttpClientBuilder(): OkHttpClient.Builder {
//        val loggingInterceptor = HttpLoggingInterceptor()
//        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val builder = OkHttpClient.Builder()
        return builder
            //设置缓存配置 缓存最大10M
            .cache(Cache(File(MainApplication.instance.cacheDir, "net_cache"), 10 * 1024 * 1024))
            //添加Cookies自动持久化
            .cookieJar(cookieJar)
            //示例：添加公共heads 注意要设置在日志拦截器之前，不然Log中会不显示head信息
            .addInterceptor(MyHeadInterceptor())
            // 日志拦截器
            .addInterceptor(LogInterceptor())
            //添加缓存拦截器 可传入缓存天数，不传默认7天
            .addInterceptor(CacheInterceptor())
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .writeTimeout(5, TimeUnit.SECONDS)

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