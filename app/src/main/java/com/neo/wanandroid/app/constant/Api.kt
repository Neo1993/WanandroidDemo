package com.neo.wanandroid.app.constant

object Api {
    const val HOST = "https://www.wanandroid.com/"

    const val BANNER = "banner/json"
    const val LOGIN = "user/login"
    const val UPLOAD = "upload"
    const val FRIEND = "friend/json"
    const val ARTICLE = "article/list/%s/json" // %s为分页索引
    const val TOP_ARTICLE = "article/top/json" // %s为分页索引
}