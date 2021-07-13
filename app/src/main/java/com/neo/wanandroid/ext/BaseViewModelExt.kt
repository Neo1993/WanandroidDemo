package com.neo.wanandroid.ext

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.neo.wanandroid.base.BaseVMActivity
import com.neo.wanandroid.base.BaseVMFragment
import com.neo.wanandroid.base.BaseViewModel
import com.neo.wanandroid.network.ApiResponse
import com.neo.wanandroid.network.RequestCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

fun <T> BaseViewModel.request(
        block: suspend () -> ApiResponse<T>,
        requestCallback: MutableLiveData<RequestCallback<T>>,
        isShowDialog: Boolean = false,
        loadingMessage: String = loadingMsg
): Job {
    return viewModelScope.launch {
        runCatching {
            if (isShowDialog) requestCallback.value = RequestCallback.onAppLoading(loadingMessage)
            block.invoke()
        }.onSuccess {
            requestCallback.value = RequestCallback.onAppSuccess(it.data)
        }.onFailure {
            requestCallback.value = RequestCallback.onAppError(Exception(it))
        }
    }
}

fun <T> BaseViewModel.request(
        block: suspend () -> ApiResponse<T>,
        success : (T) -> Unit,
        error: (Exception) -> Unit = {},
        isShowDialog: Boolean = false,
        loadingMessage: String = loadingMsg
): Job{
    return viewModelScope.launch {
        runCatching {
            if(isShowDialog) uiLoadingChange.showDialog.postValue(loadingMessage)
            block.invoke()
        }.onSuccess {
            uiLoadingChange.dismissDialog.postValue(true)
            success(it.getResponseData())
        }.onFailure {
            uiLoadingChange.dismissDialog.postValue(true)
            error(Exception(it))
        }
    }
}

fun <T> BaseVMActivity<*>.handleCallback(
        requestCallback: RequestCallback<T>,
        onSuccess: (T) -> Unit,
        onError: ((e: Exception) -> Unit)? = null,
        onLoading: (() -> Unit)? = null
) {
    when (requestCallback) {
        is RequestCallback.Loading -> {
            showLoading(requestCallback.loadingMessage)
            onLoading?.invoke()
        }
        is RequestCallback.Success -> {
            dissmissLoading()
            onSuccess(requestCallback.data)
        }
        is RequestCallback.Error -> {
            dissmissLoading()
            onError?.run {
                this(requestCallback.exception)
            }
        }
    }

}

fun <T> BaseVMFragment<*>.handleCallback(
        requestCallback: RequestCallback<T>,
        onSuccess: (T) -> Unit,
        onError: ((e: Exception) -> Unit)? = null,
        onLoading: (() -> Unit)? = null
) {
    when (requestCallback) {
        is RequestCallback.Loading -> {
            showLoading(requestCallback.loadingMessage)
            onLoading?.invoke()
        }
        is RequestCallback.Success -> {
            dissmissLoading()
            onSuccess(requestCallback.data)
        }
        is RequestCallback.Error -> {
            dissmissLoading()
            onError?.run {
                this(requestCallback.exception)
            }
        }
    }

}
