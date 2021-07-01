package com.neo.wanandroid.ext

import androidx.recyclerview.widget.RecyclerView
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