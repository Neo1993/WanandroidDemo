package com.neo.wanandroid.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.neo.wanandroid.R
import com.neo.wanandroid.ext.setAdapterAnimation
import com.neo.wanandroid.model.bean.SearchResponse

class HotSearchAdapter : BaseQuickAdapter<SearchResponse, BaseViewHolder>(R.layout.item_hot_search) {
    init {
        setAdapterAnimation(2)
    }

    override fun convert(holder: BaseViewHolder, item: SearchResponse) {
        holder.setText(R.id.keyTV, item.name)
    }
}