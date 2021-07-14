package com.neo.wanandroid.ext

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.neo.wanandroid.base.BaseVMActivity
import com.neo.wanandroid.base.BaseVMFragment
import com.neo.wanandroid.base.BaseViewModel
import com.neo.wanandroid.network.*
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
        error: (AppException) -> Unit = {},
        isShowDialog: Boolean = false,
        loadingMessage: String = loadingMsg
): Job{
    return viewModelScope.launch {
        runCatching {
            if(isShowDialog) uiLoadingChange.showDialog.postValue(loadingMessage)
            //请求体
            block.invoke()
        }.onSuccess { apiResponse ->
            //网络请求成功 关闭弹窗
            uiLoadingChange.dismissDialog.postValue(true)
            runCatching {
                //校验请求结果码是否正确，不正确会抛出异常走下面的onFailure
                executeResponse(apiResponse){
                    success(it)
                }
            }.onFailure {
                //打印错误信息
                it.message?.loge()
                //失败回调
                error(ExceptionHandle.handleException(it))
            }
            success(apiResponse.getResponseData())
        }.onFailure {
            //网络请求异常 关闭弹窗
            uiLoadingChange.dismissDialog.postValue(true)
            //打印错误信息
            it.message?.loge()
            //失败回调
            error(ExceptionHandle.handleException(it))
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

/**
 *
 */
suspend fun <T> executeResponse(
        response: BaseResponse<T>,
        success: suspend (T) -> Unit
) {
    coroutineScope{
        when {
            response.isSuccess() -> {
                success(response.getResponseData())
            }
            else -> {
                throw AppException(
                        response.getResponseCode(),
                        response.getResponseMsg()
                )
            }
        }
    }
}
