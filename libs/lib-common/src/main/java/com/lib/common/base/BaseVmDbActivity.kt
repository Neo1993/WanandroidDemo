package com.lib.common.base

import android.view.View
import androidx.databinding.ViewDataBinding
import com.lib.common.ext.inflateBindingWithGeneric

abstract class BaseVmDbActivity<VM : BaseViewModel, DB : ViewDataBinding> : BaseVmActivity<VM>() {
    lateinit var mDataBinding: DB

    override fun getLayoutId(): Int = 0

    public override fun initDataBinding(): View {
        mDataBinding = inflateBindingWithGeneric(layoutInflater)
        return mDataBinding.root
    }
}