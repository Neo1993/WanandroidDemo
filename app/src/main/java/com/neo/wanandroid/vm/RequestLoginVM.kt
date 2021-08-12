package com.neo.wanandroid.vm

import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.neo.wanandroid.base.BaseViewModel
import com.neo.wanandroid.ext.request
import com.neo.wanandroid.model.bean.User
import com.neo.wanandroid.network.apiService

class RequestLoginVM: BaseViewModel() {
    var user: MutableLiveData<User> = MutableLiveData()

    var username = ObservableField<String>()
    var password = ObservableField<String>()
    var isShowPwd = ObservableField<Boolean>()
    var pwdVisiable = object : ObservableField<Int>(password) {
        override fun get(): Int? {
            return if(password.get().isNullOrEmpty()) View.GONE else View.VISIBLE
        }
    }

    fun login(username: String, password: String){
       request({apiService.login(username, password)}, {
//           user.value = it;
       })
    }

}