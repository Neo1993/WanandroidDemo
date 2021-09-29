package com.neo.wanandroid.base

import android.os.Bundle
import android.os.PersistableBundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.launcher.ARouter

abstract class BaseActivity : AppCompatActivity(){
    @LayoutRes
    abstract fun getLayoutId() : Int
    abstract fun showLoading(loadingMessage : String  = "正在加载中")
    abstract fun dissmissLoading()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ARouter.getInstance().inject(this)
    }

    fun goActivity(path: String){
        ARouter.getInstance().build(path).navigation()
    }

    fun goActivity(path: String, bundle: Bundle){
        ARouter.getInstance().build(path).with(bundle).navigation();
    }

}