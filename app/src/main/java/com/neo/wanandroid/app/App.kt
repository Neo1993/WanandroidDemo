package com.neo.wanandroid.app

import android.app.Application
import com.kingja.loadsir.LoadSirUtil
import com.kingja.loadsir.callback.SuccessCallback
import com.kingja.loadsir.core.LoadSir
import com.neo.wanandroid.base.BaseApp
import com.neo.wanandroid.ext.loadingMsg
import com.neo.wanandroid.ui.widget.loadcallback.EmptyCallback
import com.neo.wanandroid.ui.widget.loadcallback.ErrorCallback
import com.neo.wanandroid.ui.widget.loadcallback.LoadingCallback

class App : BaseApp() {
    companion object {
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        instance = this;

        LoadSir.beginBuilder()
            .addCallback(LoadingCallback())             //加载页
            .addCallback(ErrorCallback())               //错误提示页
            .addCallback(EmptyCallback())               //空数据提示页
            .setDefaultCallback(LoadingCallback::class.java)
            .commit()
    }

}