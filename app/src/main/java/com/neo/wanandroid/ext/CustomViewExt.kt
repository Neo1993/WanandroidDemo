package com.neo.wanandroid.ext

import android.app.Activity
import android.graphics.Path
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.kingja.loadsir.core.LoadService
import com.neo.wanandroid.R
import com.neo.wanandroid.model.bean.ApiPageResponse
import com.neo.wanandroid.model.bean.ListDataUiState
import com.neo.wanandroid.ui.widget.loadcallback.EmptyCallback
import com.neo.wanandroid.ui.widget.loadcallback.ErrorCallback
import com.neo.wanandroid.ui.widget.loadcallback.LoadingCallback
import com.scwang.smart.refresh.layout.SmartRefreshLayout
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
 * 初始化RcyclcerView
 */
fun RecyclerView.init(
        bindManager: RecyclerView.LayoutManager,
        bindAdapter: RecyclerView.Adapter<*>,
        isScroll: Boolean = true,
        hasFixedSize: Boolean = true
): RecyclerView {
    layoutManager = bindManager;
    adapter = bindAdapter
    isNestedScrollingEnabled = isScroll
    setHasFixedSize(hasFixedSize)
    return this
}

/**
 * 初始化SwipeRecyclerView
 */
fun SwipeRecyclerView.init(
    bindManager: RecyclerView.LayoutManager,
    bindAdapter: RecyclerView.Adapter<*>,
    isScroll: Boolean = true,
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

fun RecyclerView.initFloatBT(floatBT: FloatingActionButton){
    //监听recyclerview滑动到顶部的时候，需要把向上返回顶部的按钮隐藏
    addOnScrollListener(object : RecyclerView.OnScrollListener(){
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if(!canScrollVertically(-1) ){         //不能向下滚动,即滚动到顶部时
                floatBT.visibility = View.INVISIBLE
            }
        }
    })

    floatBT.setOnClickListener {
        val layoutManager = layoutManager as LinearLayoutManager
        //如果当前recyclerview 最后一个视图位置的索引大于等于40，则迅速返回顶部，否则带有滚动动画效果返回到顶部
        if(layoutManager.findLastCompletelyVisibleItemPosition() >= 40 ){
            scrollToPosition(0)//没有动画迅速返回到顶部(马上)
        }else{
            smoothScrollToPosition(0)//有滚动动画返回到顶部(有点慢)
        }
    }
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

/**
 * 初始化拥有返回键的toolbar
 */
fun Toolbar.initClose(title: String = "",
                      backImgId: Int = R.drawable.ic_back,
                      onBack: (toolbar: Toolbar) -> Unit
) {
    this.title = title
    setNavigationIcon(backImgId)
    setNavigationOnClickListener {
        onBack(this)
    }
}


/**
 * 隐藏软键盘
 */
fun hideSoftKeyboard(activity: Activity?) {
    activity?.let { act ->
        val view = act.currentFocus
        view?.let {
            val inputMethodManager =
                    act.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(
                    view.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }
}
