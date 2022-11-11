package com.neo.wanandroid.model.bean

import kotlinx.serialization.Serializable

@Serializable
data class TagResponse(var name: String = "", var url: String)