package com.neo.wanandroid.vm

import android.view.View
import androidx.databinding.ObservableField
import com.neo.wanandroid.app.appVM
import com.neo.wanandroid.base.BaseViewModel
import com.neo.wanandroid.utils.CacheUtil
import com.neo.wanandroid.utils.ColorUtil

class MineVM : BaseViewModel() {

//    var username = object : ObservableField<String>(){
//        override fun get(): String? {
//            val user = CacheUtil.getUser()
//            return if(user == null){
//                info.set("id：--　排名：-")
//                "请先登录"
//            } else{
//                info.set("id: ${user.id} 排名: 0")
//                if(user.nickname.isEmpty()) user.username else user.nickname
//            }
//        }
//    }

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
