package com.neo.wanandroid.model.bean;

data class User(
    var admin: Boolean,
    var chapterTops: List<Any>,
    var coinCount: Int,
    var collectIds: List<Any>,
    var email: String,
    var icon: String,
    var id: Int,
    var nickname: String,
    var password: String,
    var publicName: String,
    var token: String,
    var type: Int,
    var username: String
)