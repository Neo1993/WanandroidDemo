package com.neo.wanandroid.vm

import android.view.View
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import com.neo.wanandroid.base.BaseViewModel

class RequestRegisterVM: BaseViewModel() {
    var username = ObservableField<String>()
    var clearVisible = object: ObservableInt(username){
        override fun get(): Int {
            return if(username.get().isNullOrEmpty()) {
                View.GONE
            }else{
                View.VISIBLE
            }
        }
    }
}