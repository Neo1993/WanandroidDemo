package com.neo.wanandroid.ext

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.neo.wanandroid.base.BaseViewModel
import com.neo.wanandroid.network.ApiResponse
import com.neo.wanandroid.network.BaseResponse
import com.neo.wanandroid.network.RequestCallback
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

fun <T> BaseViewModel.request(
        block: suspend () -> ApiResponse<T>,
        requestCallback: MutableLiveData<RequestCallback<T>>,
        isShowDialog: Boolean = true,
        loadingMessage: String = "请求网络中"
): Job {
    return viewModelScope.launch {
        runCatching {
            if(isShowDialog) requestCallback.value = RequestCallback.onAppLoading(loadingMessage)
            block.invoke()
        }.onSuccess {

        }.onFailure {

        }

    }
}
