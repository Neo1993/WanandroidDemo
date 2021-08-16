package com.neo.wanandroid.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.neo.wanandroid.ext.dismissLoadingExt
import com.neo.wanandroid.ext.getVMClazz
import com.neo.wanandroid.ext.showLoadingExt

abstract class BaseVmDbFragment<VM : BaseViewModel, DB : ViewDataBinding> : BaseVMFragment<VM>() {
    //该类绑定的ViewDataBinding
    lateinit var mDataBind: DB

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mDataBind = DataBindingUtil.inflate(layoutInflater, getLayoutId(), container, false)
        mDataBind.lifecycleOwner = this
        mFragmentView = mDataBind.root
        return mFragmentView
    }

}