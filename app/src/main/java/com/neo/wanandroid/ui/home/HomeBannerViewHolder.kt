package com.neo.wanandroid.ui.home

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.neo.wanandroid.R
import com.neo.wanandroid.app.App
import com.neo.wanandroid.model.bean.BannerResponse
import com.zhpan.bannerview.BaseViewHolder

class HomeBannerViewHolder(view : View) : BaseViewHolder<BannerResponse>(view){
    override fun bindData(data: BannerResponse?, position: Int, pageSize: Int) {
        val bannerIV = itemView.findViewById<ImageView>(R.id.bannerIV)
        Glide.with(App.instance)
            .load(data?.imagePath)
            .transition(DrawableTransitionOptions.withCrossFade(500))
            .into(bannerIV)
    }

}