package com.neo.wanandroid.base

import androidx.databinding.ViewDataBinding
import com.lib.common.base.BaseViewModel
import com.lib.common.base.BaseVmDbActivity
import com.neo.wanandroid.ext.dismissLoadingExt
import com.neo.wanandroid.ext.showLoadingExt

abstract class BaseActivity<VM : BaseViewModel, DB : ViewDataBinding> : BaseVmDbActivity<VM, DB>() {
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