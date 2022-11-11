package com.lib.common.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * ViewModel的基类
 */
open class BaseViewModel : ViewModel() {
    val uiLoadingChange: UiLoadingChange by lazy { UiLoadingChange() }

    /**
     * 内置封装好的可通知Activity/fragment 显示隐藏加载框
     */
    inner class UiLoadingChange {
        //显示加载框
        val showDialog by lazy { MutableLiveData<String>() }
        //隐藏
        val dismissDialog by lazy { MutableLiveData<Boolean>() }
    }
}