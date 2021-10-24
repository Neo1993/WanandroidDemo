package com.neo.wanandroid.utils

import android.text.TextUtils
import com.alibaba.android.arouter.launcher.ARouter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.neo.wanandroid.app.constant.PATH_ACTIVITY_LOGIN
import com.neo.wanandroid.model.bean.User
import com.neo.wanandroid.ui.mine.LoginActivity
import com.tencent.mmkv.MMKV

object CacheUtil {
    private val userKV by lazy { MMKV.mmkvWithID("app") }           //用户信息存储
    private val searchKV by lazy { MMKV.mmkvWithID("search") }         //搜索历史存储

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

    /**
     * 清除缓存的用户信息
     */
    fun removeUser(){
        userKV.clearAll()
    }

    /**
     * 获取搜索历史缓存数据
     */
    fun getSearchHistoryList(): ArrayList<String> {
        val json = searchKV.decodeString("search_history")
        if (!json.isNullOrEmpty()) {
            return Gson().fromJson(json, object : TypeToken<ArrayList<String>>() {}.type)
        }
        return arrayListOf()
    }

    /**
     * 设置搜索历史缓存数据
     */
    fun setSearchHistory(searchKeyList: ArrayList<String>){
        searchKV.encode("search_history", Gson().toJson(searchKeyList))
    }

    /**
     * 添加搜索历史缓存数据
     */
    fun addSearchHistory(searchKey: String) {
        val historyList = getSearchHistoryList()
        if (historyList.contains(searchKey)) {
            //当搜索历史中包含该数据时 删除
            historyList.remove(searchKey)
        } else if (historyList.size >= 10) {
            //如果集合的size 有10个以上了，删除最后一个
            historyList.removeAt(historyList.size - 1)
        }
        historyList.add(0, searchKey)
        searchKV.encode("search_history", Gson().toJson(historyList))
    }

    /**
     * 删除单条历史缓存数据
     */
    fun deleteSearchHistory(searchKey: String) {
        val historyList = getSearchHistoryList()
        if (historyList.contains(searchKey)) {
            //当搜索历史中包含该数据时 删除
            historyList.remove(searchKey)
        }
        searchKV.encode("search_history", Gson().toJson(historyList))
    }

    fun deleteAllSearchHistory() {
        searchKV.removeValueForKey("search_history")
    }

}