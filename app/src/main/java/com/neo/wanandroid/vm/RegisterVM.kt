package com.neo.wanandroid.vm

import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.lib.common.base.BaseViewModel
import com.neo.wanandroid.app.appVM
import com.neo.wanandroid.ext.request
import com.neo.wanandroid.network.apiService
import com.neo.wanandroid.utils.CacheUtil

class RegisterVM: BaseViewModel() {
    var regisetResult: MutableLiveData<Any> = MutableLiveData()
    var username = ObservableField<String>()
    var password = ObservableField<String>()
    var repassword = ObservableField<String>()
    var isShowPwd = ObservableField<Boolean>()
    var isShowPwd2 = ObservableField<Boolean>()
    var usernameVisiable = object : ObservableField<Int>(username) {
        override fun get(): Int? {
            return if(username.get().isNullOrEmpty()) View.GONE else View.VISIBLE
        }
    }
    var pwdVisiable = object : ObservableField<Int>(password) {
        override fun get(): Int? {
            return if(password.get().isNullOrEmpty()) View.GONE else View.VISIBLE
        }
    }

    var pwdVisiable2 = object : ObservableField<Int>(repassword) {
        override fun get(): Int? {
            return if(repassword.get().isNullOrEmpty()) View.GONE else View.VISIBLE
        }
    }

    fun register(){
        request({ apiService.register(username.get().toString(), password.get().toString(), repassword.get().toString())},{
            regisetResult.value = it
        },isShowDialog = true)
    }

    fun login(){
        request({apiService.login(username.get().toString(), password.get().toString())}, {
            CacheUtil.setUser(it)
            appVM.currentUser.value = it
        },isShowDialog = true)
    }

}