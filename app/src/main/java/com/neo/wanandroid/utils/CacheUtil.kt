package com.neo.wanandroid.utils

import android.text.TextUtils
import com.google.gson.Gson
import com.neo.wanandroid.model.bean.User
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
    fun isLogin(): Boolean {
        val currentUser = getUser()
        return currentUser != null
    }

    fun removeUser(){
        userKV.clearAll()
    }

}