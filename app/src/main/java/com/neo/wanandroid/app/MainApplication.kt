package com.neo.wanandroid.app

import android.content.Intent
import android.os.Process
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.drake.brv.utils.BRV
import com.drake.net.NetConfig
import com.drake.net.cookie.PersistentCookieJar
import com.drake.net.interceptor.LogRecordInterceptor
import com.drake.net.okhttp.setConverter
import com.drake.net.okhttp.setDebug
import com.drake.net.okhttp.setDialogFactory
import com.drake.net.okhttp.setRequestInterceptor
import com.drake.statelayout.StateConfig
import com.drake.tooltip.dialog.BubbleDialog
import com.lib.common.base.BaseApplication
import com.neo.wanandroid.BR
import com.neo.wanandroid.BuildConfig
import com.neo.wanandroid.MainActivity
import com.neo.wanandroid.R
import com.neo.wanandroid.app.constant.Api
import com.neo.wanandroid.app.event.EventVM
import com.neo.wanandroid.converter.SerializationConverter
import com.neo.wanandroid.interceptor.MyRequestInterceptor
import com.neo.wanandroid.vm.AppVM
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.MaterialHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.tencent.mmkv.MMKV
import okhttp3.Cache
import java.util.concurrent.TimeUnit

val appVM by lazy { MainApplication.appVMInstance }
val eventVM by lazy { MainApplication.eventVM }


class MainApplication : BaseApplication() {
    companion object {
        lateinit var instance: MainApplication
        lateinit var appVMInstance: AppVM
        lateinit var eventVM: EventVM

//        init {
//            SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
//                layout.setPrimaryColorsId(R.color.colorPrimary, R.color.white)
//                ClassicsHeader(context)
//            }
//
//            SmartRefreshLayout.setDefaultRefreshFooterCreator { context, layout ->
//                ClassicsFooter(context).setDrawableSize(20f)
//            }
//
//        }
    }

    override fun onCreate() {
        super.onCreate()
        MMKV.initialize(filesDir.absolutePath + "/mmkv")
        instance = this;
        appVMInstance = getAppViewModelProvider().get(AppVM::class.java)
        eventVM = EventVM()

        //创建服务用于捕获崩溃异常
//        Thread.setDefaultUncaughtExceptionHandler { t, e ->
//                e.printStackTrace()
//                restartApp()
//        }

        initNetConfig()
        initializeThirdPart()
    }

    private fun initNetConfig() {
        NetConfig.initialize(Api.HOST, this) {

            // 超时设置
            connectTimeout(30, TimeUnit.SECONDS)
            readTimeout(30, TimeUnit.SECONDS)
            writeTimeout(30, TimeUnit.SECONDS)

            // 本框架支持Http缓存协议和强制缓存模式
            cache(Cache(cacheDir, 1024 * 1024 * 128)) // 缓存设置, 当超过maxSize最大值会根据最近最少使用算法清除缓存来限制缓存大小

            // LogCat是否输出异常日志, 异常日志可以快速定位网络请求错误
            setDebug(BuildConfig.DEBUG)

            // AndroidStudio OkHttp Profiler 插件输出网络日志
            addInterceptor(LogRecordInterceptor(BuildConfig.DEBUG))

            // 添加持久化Cookie管理
            cookieJar(PersistentCookieJar(this@MainApplication))

            // 通知栏监听网络日志
            if (BuildConfig.DEBUG) {
                addInterceptor(
                    ChuckerInterceptor.Builder(this@MainApplication)
                        .collector(ChuckerCollector(this@MainApplication))
                        .maxContentLength(250000L)
                        .redactHeaders(emptySet())
                        .alwaysReadResponseBody(false)
                        .build()
                )
            }

            // 添加请求拦截器, 可配置全局/动态参数
            setRequestInterceptor(MyRequestInterceptor())

            // 数据转换器
            setConverter(SerializationConverter())

            // 自定义全局加载对话框
            setDialogFactory {
                BubbleDialog(it, "加载中....")
            }
        }
    }

    /** 初始化第三方依赖库库 */
    private fun initializeThirdPart() {

        // 全局缺省页配置 [https://github.com/liangjingkanji/StateLayout]
        StateConfig.apply {
            emptyLayout = R.layout.layout_empty
            loadingLayout = R.layout.layout_loading
            errorLayout = R.layout.layout_error
            setRetryIds(R.id.retryTV, R.id.error_text)
        }

        // 初始化SmartRefreshLayout, 这是自动下拉刷新和上拉加载采用的第三方库  [https://github.com/scwang90/SmartRefreshLayout/tree/master] V2版本
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, _ ->
            MaterialHeader(context)
        }

        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, _ ->
            ClassicsFooter(context)
        }

        BRV.modelId = BR.m
        BRV.clickThrottle = 1000
    }

    /**
     * 重启app
     */
    private fun restartApp() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        Process.killProcess(Process.myPid()) //结束进程之前可以把你程序的注销或者退出代码放在这段代码之前
    }


}