package com.neo.wanandroid.app

import android.app.Application
import com.neo.wanandroid.base.BaseApp
import com.neo.wanandroid.ext.loadingMsg

class App : BaseApp() {
    companion object {
        lateinit var instance : App
    }

    override fun onCreate() {
        super.onCreate()
        instance = this;
    }

}