package com.neo.wanandroid.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {
    val uiLoadingChange : UiLoadingChange by lazy { UiLoadingChange() }

    inner class UiLoadingChange{
        //显示加载框
        val showDialog by lazy { MutableLiveData<String>() }
        //隐藏加载框
        val dismissDialog by lazy { MutableLiveData<Boolean>() }
    }

}