package com.neo.wanandroid.ext

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.ToastUtils
import com.lib.common.base.BaseViewModel
import com.neo.wanandroid.base.BaseActivity
import com.neo.wanandroid.base.BaseFragment
import com.neo.wanandroid.network.*
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
                if(isShowDialog){
                    ToastUtils.showShort(it.message)
                }
            }
        }.onFailure {
            //网络请求异常 关闭弹窗
            uiLoadingChange.dismissDialog.postValue(true)
            //打印错误信息
            it.message?.loge()
            //失败回调
            error(ExceptionHandle.handleException(it))
            if(isShowDialog){
                ToastUtils.showShort(it.message)
            }
        }
    }
}

fun <T> BaseActivity<*,*>.handleCallback(
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
            dismissLoading()
            onSuccess(requestCallback.data)
        }
        is RequestCallback.Error -> {
            dismissLoading()
            onError?.run {
                this(requestCallback.exception)
            }
        }
    }
}

fun <T> BaseFragment<*,*>.handleCallback(
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
            dismissLoading()
            onSuccess(requestCallback.data)
        }
        is RequestCallback.Error -> {
            dismissLoading()
            onError?.run {
                this(requestCallback.exception)
            }
        }
    }
}

/**
 *  解析响应数据
 *  @param 响应的数据
 *  @param 成功回调
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

/**
 *
 */
fun <T> BaseViewModel.launch(
    block: suspend () -> T,
    success: (T) -> Unit,
    error: (Throwable) -> Unit = {}
) {
    viewModelScope.launch {
        runCatching {
            block()
        }.onSuccess {
            success(it)
        }.onFailure {
            error(it)
        }
    }
}
