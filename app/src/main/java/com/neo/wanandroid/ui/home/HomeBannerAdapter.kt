package com.neo.wanandroid.ui.home

import android.view.View
import com.neo.wanandroid.R
import com.neo.wanandroid.model.bean.BannerResponse
import com.zhpan.bannerview.BaseBannerAdapter

class HomeBannerAdapter : BaseBannerAdapter<BannerResponse, HomeBannerViewHolder>() {
    override fun getLayoutId(viewType: Int): Int {
        return R.layout.item_home_banner
    }

    override fun createViewHolder(itemView: View, viewType: Int): HomeBannerViewHolder {
        return HomeBannerViewHolder(itemView)
    }

    override fun onBind(
        holder: HomeBannerViewHolder?,
        data: BannerResponse?,
        position: Int,
        pageSize: Int
    ) {
        holder?.bindData(data, position, pageSize)
    }

}