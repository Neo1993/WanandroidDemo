package com.neo.wanandroid.network

import androidx.lifecycle.MutableLiveData
import kotlin.Exception

sealed class RequestCallback<out T>{
    companion object {
        fun <T> onAppLoading(loadingMessage : String) : RequestCallback<T> = Loading(loadingMessage)
        fun <T> onAppSuccess(data : T) : RequestCallback<T> = Success(data)
        fun <T> onAppError(exception: Exception) : RequestCallback<T> = Error(exception)
    }

    data class Loading(val loadingMessage : String) : RequestCallback<Nothing>()
    data class Success<T>(val data: T) : RequestCallback<T>()
    data class Error<T>(val exception : Exception) : RequestCallback<T>()
}

//fun <T> MutableLiveData<RequestCallback<T>>.handleCallback(result : BaseResponse<T>){
//    value = when {
//        result.isSuccess() -> RequestCallback.onAppSuccess(result.getResponseData())
//        else -> RequestCallback.onAppError(Exception(result.getResponseMsg()))
//    }
//}