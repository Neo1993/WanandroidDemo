package com.neo.wanandroid.base

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseVmDbActivity<VM: BaseViewModel, DB: ViewDataBinding> : BaseVMActivity<VM>(){
    lateinit var mDatabind: DB

    override fun onCreate(savedInstanceState: Bundle?) {
        useDataBinding(true)
        super.onCreate(savedInstanceState)
    }

    /**
     * 创建DataBinding
     */
    override fun initDataBind() {
        mDatabind = DataBindingUtil.setContentView(this, getLayoutId())
        mDatabind.lifecycleOwner = this
    }
}