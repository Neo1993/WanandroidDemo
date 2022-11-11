package com.neo.wanandroid.base

import androidx.databinding.ViewDataBinding
import com.lib.common.base.BaseViewModel
import com.lib.common.base.BaseVmDbFragment
import com.neo.wanandroid.ext.dismissLoadingExt
import com.neo.wanandroid.ext.showLoadingExt

abstract class BaseFragment<VM : BaseViewModel, DB : ViewDataBinding> : BaseVmDbFragment<VM, DB>() {
    /**
     * 打开等待框
     */
    fun showLoading(message: String) {
        showLoadingExt(message)
    }

    /**
     * 关闭等待框
     */
    fun dismissLoading() {
        dismissLoadingExt()
    }
}