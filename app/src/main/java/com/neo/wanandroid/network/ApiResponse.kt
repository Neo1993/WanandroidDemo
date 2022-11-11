package com.neo.wanandroid.network

import kotlinx.serialization.Serializable

@Serializable
class ApiResponse<T>(val errorCode: Int, val data: T, val errorMsg: String) : BaseResponse<T> {

    override fun isSuccess(): Boolean = errorCode == 0

    override fun getResponseCode(): Int = errorCode

    override fun getResponseData(): T = data

    override fun getResponseMsg(): String = errorMsg
}