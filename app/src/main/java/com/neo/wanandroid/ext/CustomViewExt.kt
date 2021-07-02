package com.neo.wanandroid.ext

import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.yanzhenjie.recyclerview.SwipeRecyclerView

//初始化SwipeRecyclerView
fun SwipeRecyclerView.init(
    bindManager: RecyclerView.LayoutManager,
    bindAdapter: RecyclerView.Adapter<*>,
    isScroll: Boolean = true
): SwipeRecyclerView {
    layoutManager = bindManager;
    adapter = bindAdapter
    isNestedScrollingEnabled = isScroll
    return this
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