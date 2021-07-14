package com.neo.wanandroid.model.bean

data class ListDataUiState<T>(
    var isSuccess: Boolean,                 //是否请求成功
    var isEmpty: Boolean = false,                   //是否为空
    var isFirstEmpty: Boolean = false,              //第一页没有数据
    var isRefresh: Boolean = false,                 //是否是刷新
    var hasMore: Boolean = false,                   //是否可以加载更多
    var errorMsg: String = "",                  //错误信息
    var dataList: ArrayList<T>              //数据列表
)
