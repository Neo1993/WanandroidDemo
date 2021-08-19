package com.neo.wanandroid.base

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner

open class BaseApp : Application(), ViewModelStoreOwner {
    private lateinit var mAppViewModelStore: ViewModelStore
    override fun onCreate() {
        super.onCreate()
        mAppViewModelStore = ViewModelStore()
    }

    override fun getViewModelStore(): ViewModelStore {
        return mAppViewModelStore
    }

    fun getAppViewModelProvider(): ViewModelProvider {
        return ViewModelProvider(this)
    }
}