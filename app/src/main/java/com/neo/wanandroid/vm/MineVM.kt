package com.neo.wanandroid.vm

import androidx.databinding.ObservableField
import com.lib.common.base.BaseViewModel
import com.neo.wanandroid.utils.ColorUtil

class MineVM : BaseViewModel() {
    var username = ObservableField<String>()

    var info = ObservableField<String>()

    var headImgUrl = ObservableField<String>(ColorUtil.randomImage())

    var logoutVisible = ObservableField<Int>()
//    var logoutVisible = object : ObservableField<Int>() {
//        override fun get(): Int? {
//            return if(CacheUtil.isLogin()) View.VISIBLE else View.GONE
//        }
//    }

}
