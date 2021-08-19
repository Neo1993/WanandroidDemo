package com.neo.wanandroid.vm

import androidx.lifecycle.MutableLiveData
import com.neo.wanandroid.base.BaseViewModel
import com.neo.wanandroid.model.bean.User
import com.neo.wanandroid.utils.CacheUtil

/**
 * App全局的ViewModel,可以存放公共数据，当他数据改变时，所有监听他的地方都会收到回调,也可以做发送消息
 * 比如 全局可使用的 地理位置信息，账户信息,App的基本配置等等，
 */
class AppVM: BaseViewModel() {
    var currentUser = MutableLiveData<User>()

    init {
        currentUser.value = CacheUtil.getUser()
    }

}