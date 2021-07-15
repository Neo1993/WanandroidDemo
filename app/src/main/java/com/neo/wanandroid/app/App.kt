package com.neo.wanandroid.app

import com.kingja.loadsir.core.LoadSir
import com.neo.wanandroid.R
import com.neo.wanandroid.base.BaseApp
import com.neo.wanandroid.ui.widget.loadcallback.EmptyCallback
import com.neo.wanandroid.ui.widget.loadcallback.ErrorCallback
import com.neo.wanandroid.ui.widget.loadcallback.LoadingCallback
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout

class App : BaseApp() {
    companion object {
        lateinit var instance: App

        init {
            SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
                layout.setPrimaryColorsId(R.color.colorPrimary, R.color.white)
                ClassicsHeader(context)
            }

            SmartRefreshLayout.setDefaultRefreshFooterCreator { context, layout ->
                ClassicsFooter(context).setDrawableSize(20f)
            }

        }
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