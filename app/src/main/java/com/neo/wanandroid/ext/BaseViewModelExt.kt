package com.neo.wanandroid.ext

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.neo.wanandroid.base.BaseVMActivity
import com.neo.wanandroid.base.BaseVMFragment
import com.neo.wanandroid.base.BaseViewModel
import com.neo.wanandroid.network.ApiResponse
import com.neo.wanandroid.network.RequestCallback
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

fun <T> BaseViewModel.request(
        block: suspend () -> ApiResponse<T>,
        requestCallback: MutableLiveData<RequestCallback<T>>,
        isShowDialog: Boolean = true,
        loadingMessage: String = loadingMsg
): Job {
    return viewModelScope.launch {
        runCatching {
            if (isShowDialog) requestCallback.value = RequestCallback.onAppLoading(loadingMessage)
            block.invoke()
        }.onSuccess {

        }.onFailure {

        }
    }

}

fun <T> BaseVMActivity<*>.handleCallback(
        requestCallback: RequestCallback<T>,
        onSuccess: (T) -> Unit,
        onError: ((e: Exception) -> Unit),
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
            onError.run {
                this(requestCallback.exception)
            }
//                onError(Exception(requestCallback.exception))
        }
    }

}

fun <T> BaseVMFragment<*>.handleCallback(
        requestCallback: RequestCallback<T>,
        onSuccess: (T) -> Unit,
        onError: ((e: Exception) -> Unit),
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
            onError.run {
                this(requestCallback.exception)
            }
        }
    }

}
