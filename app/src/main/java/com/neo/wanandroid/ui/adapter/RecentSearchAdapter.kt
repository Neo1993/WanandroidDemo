package com.neo.wanandroid.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.neo.wanandroid.R
import com.neo.wanandroid.ext.setAdapterAnimation

class RecentSearchAdapter : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_recent_history) {
    private var isEdit: Boolean = false
    init {
        setAdapterAnimation(2)
        addChildClickViewIds(R.id.deleteIV)
    }
    override fun convert(holder: BaseViewHolder, item: String) {
        holder.setText(R.id.keyTV, item)
        holder.setVisible(R.id.deleteIV, isEdit)
    }

    fun setEditState(isEdit: Boolean){
        this.isEdit = isEdit
        notifyDataSetChanged()
    }

}