package com.neo.wanandroid.model.bean

import kotlinx.serialization.Serializable

/**
 * 首页轮播图
 */
@Serializable
data class BannerResponse(
    var desc: String = "",
    var id: Int = 0,
    var imagePath: String = "",
    var isVisible: Int = 0,
    var order: Int = 0,
    var title: String = "",
    var type: Int = 0,
    var url: String = ""
)