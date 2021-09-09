package com.neo.wanandroid.utils

import android.text.TextUtils
import com.alibaba.android.arouter.launcher.ARouter
import com.google.gson.Gson
import com.neo.wanandroid.app.constant.PATH_ACTIVITY_LOGIN
import com.neo.wanandroid.model.bean.User
import com.neo.wanandroid.ui.mine.LoginActivity
import com.tencent.mmkv.MMKV

object CacheUtil {
    private val userKV by lazy { MMKV.mmkvWithID("app") }           //用户信息存储

    /**
     * 获取保存的用户信息
     */
    fun getUser(): User? {
        val userStr = userKV.decodeString("user")
        return if (TextUtils.isEmpty(userStr)) {
            null
        } else {
            Gson().fromJson(userStr, User::class.java)
        }
    }

    /**
     * 设置用户信息
     */
    fun setUser(userResponse: User?) {
        if (userResponse != null) {
            userKV.encode("user", Gson().toJson(userResponse))
        }
    }

    /**
     * 是否已经登录
     */
    fun isLogin(goLogin: Boolean = false): Boolean {
        val currentUser = getUser()
        val isLogin = currentUser != null
        if(!isLogin && goLogin){
            ARouter.getInstance().build(PATH_ACTIVITY_LOGIN).navigation()
        }

        return isLogin
    }

    fun removeUser(){
        userKV.clearAll()
    }

}