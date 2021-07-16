package com.neo.wanandroid.base

import android.os.Bundle
import android.os.PersistableBundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(getLayoutId())
    }

    abstract fun showLoading(loadingMessage : String  = "正在加载中")
    abstract fun dissmissLoading()
    @LayoutRes
    abstract fun getLayoutId() : Int
}