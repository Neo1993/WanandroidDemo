package com.neo.wanandroid.app

import android.content.Intent
import android.os.Process
import androidx.annotation.MainThread
import androidx.lifecycle.ViewModelProvider
import com.kingja.loadsir.core.LoadSir
import com.neo.wanandroid.MainActivity
import com.neo.wanandroid.R
import com.neo.wanandroid.base.BaseApp
import com.neo.wanandroid.ext.getVMClazz
import com.neo.wanandroid.ui.widget.loadcallback.EmptyCallback
import com.neo.wanandroid.ui.widget.loadcallback.ErrorCallback
import com.neo.wanandroid.ui.widget.loadcallback.LoadingCallback
import com.neo.wanandroid.vm.AppVM
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.tencent.mmkv.MMKV
import java.time.Instant

val appVM by lazy { App.appVMInstance }

class App : BaseApp() {
    companion object {
        lateinit var instance: App
        lateinit var appVMInstance: AppVM

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
        MMKV.initialize(filesDir.absolutePath + "/mmkv")
        instance = this;
        appVMInstance = getAppViewModelProvider().get(AppVM::class.java)

        //创建服务用于捕获崩溃异常
        Thread.setDefaultUncaughtExceptionHandler { t, e ->
                e.printStackTrace()
                restartApp()
        }

        LoadSir.beginBuilder()
            .addCallback(LoadingCallback())             //加载页
            .addCallback(ErrorCallback())               //错误提示页
            .addCallback(EmptyCallback())               //空数据提示页
            .setDefaultCallback(LoadingCallback::class.java)
            .commit()
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