package com.neo.wanandroid.ext

import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.kingja.loadsir.core.LoadService
import com.neo.wanandroid.model.bean.ApiPageResponse
import com.neo.wanandroid.model.bean.ListDataUiState
import com.neo.wanandroid.ui.widget.loadcallback.EmptyCallback
import com.neo.wanandroid.ui.widget.loadcallback.ErrorCallback
import com.neo.wanandroid.ui.widget.loadcallback.LoadingCallback
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.yanzhenjie.recyclerview.SwipeRecyclerView
import kotlinx.android.synthetic.main.fragment_home.view.*

/**
 * 自定义类的拓展函数
 */

/**
 * 设置加载布局
 */
fun LoadService<*>.showLoading(){
    showCallback(LoadingCallback::class.java)
}

/**
 * 设置空布局
 */
fun LoadService<*>.showEmpty(){
    showCallback(EmptyCallback::class.java)
}

/**
 * 设置错误布局
 * @param message   错误布局显示的提示内容
 */
fun LoadService<*>.showError(message: String= ""){
    showCallback(ErrorCallback::class.java)
}

/**
 * 初始化SwipeRecyclerView
 */
fun SwipeRecyclerView.init(
    bindManager: RecyclerView.LayoutManager,
    bindAdapter: RecyclerView.Adapter<*>,
    isScroll: Boolean = false,
    hasFixedSize: Boolean = true
): SwipeRecyclerView {
    layoutManager = bindManager;
    adapter = bindAdapter
    isNestedScrollingEnabled = isScroll
    setHasFixedSize(hasFixedSize)
    return this
}

fun SwipeRefreshLayout.init(onRefresh: () -> Unit){
    setOnRefreshListener(onRefresh)
//    setOnRefreshListener {
//        onRefresh.invoke()
//    }
}

/**
 * 初始化SmartRefreshLayout的下拉刷新和上拉加载
 * @param 下拉刷新
 * @param 上拉加载
 */
fun SmartRefreshLayout.init(onRefresh: () -> Unit, onLoadMore: () -> Unit){
    setEnableLoadMore(true)
   setOnRefreshListener{
       onRefresh.invoke()
   }

    setOnLoadMoreListener {
        onLoadMore.invoke()
    }

}

//设置适配器的列表动画
fun BaseQuickAdapter<*, *>.setAdapterAnimation(mode: Int) {
    //等于0,关闭列表动画,否则开启
    if (mode == 0) {
        animationEnable = false
    } else {
        animationEnable = true
        setAnimationWithDefault(BaseQuickAdapter.AnimationType.values()[mode - 1])
    }
}

fun <T> ListDataUiState<*>.build(isSuccess: Boolean, isRefresh: Boolean, apiPageResponse: ApiPageResponse<ArrayList<T>>, errorMsg: String = ""): ListDataUiState<*> {
    if (isSuccess) {
        return ListDataUiState(
                isSuccess = true,
                isEmpty = apiPageResponse.isEmpty(),
                isFirstEmpty = isRefresh && apiPageResponse.isEmpty(),
                isRefresh = isRefresh,
                hasMore = !apiPageResponse.over,
                dataList = apiPageResponse.datas
        )
    } else {
        return ListDataUiState(
                isSuccess = false,
                errorMsg = errorMsg,
                isRefresh = isRefresh,
                dataList = arrayListOf<T>()
        )
    }
}

