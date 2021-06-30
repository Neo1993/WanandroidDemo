package com.neo.wanandroid.base

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.neo.wanandroid.ext.getVMClazz


/**
 * MVVM项目中Activity的基类
 */
abstract class BaseVMActivity<VM : BaseViewModel> : BaseActivity() {
    lateinit var mViewModel: VM
    abstract fun showLoading(loadingMessage : String  = "正在加载中")
    abstract fun dissmissLoading()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        init(savedInstanceState)
    }

    private fun init(savedInstanceState: Bundle?) {
        mViewModel = createViewModel()
    }


    private fun createViewModel(): VM {
        return ViewModelProvider(this).get(getVMClazz(this))
    }



}