package com.neo.wanandroid.model.bean

data class ApiPageResponse<T>(
    var curPage: Int = 0,
    var datas: T,
    var offset: Int = 0,
    var over: Boolean = false,
    var pageCount: Int = 0,
    var size: Int = 0,
    var total: Int = 0,
)