package com.neo.wanandroid.model.bean

data class ListDataUiState<T>(
    var isSuccess: Boolean,                 //是否请求成功
    var isFirstEmpty: Boolean,              //第一页没有数据
    var isEmpty: Boolean,                   //是否为空
    var isRefresh: Boolean,                 //是否是刷新
    var hasMore: Boolean,                   //是否可以加载更多
    var errorMsg: Boolean,                  //错误信息
    var dataList: ArrayList<T>              //数据列表
)
