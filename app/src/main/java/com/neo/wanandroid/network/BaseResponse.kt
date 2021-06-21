package com.neo.wanandroid.network

interface BaseResponse<T> {
    /**
     * 是否请求成功
     */
    fun isSuccess() : Boolean

    /**
     * 响应码
     */
    fun getResponseCode() : Int

    /**
     * 响应的数据
     */
    fun getResponseData() : T

    /**
     * 返回的请求错误提示
     */
    fun getResponseMsg() : String
}