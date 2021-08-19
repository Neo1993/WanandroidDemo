package com.neo.wanandroid.ui.mine

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.neo.wanandroid.R
import com.neo.wanandroid.app.appVM
import com.neo.wanandroid.base.BaseVMFragment
import com.neo.wanandroid.base.BaseVmDbFragment
import com.neo.wanandroid.databinding.FragmentMineBinding
import com.neo.wanandroid.ext.showMessage
import com.neo.wanandroid.utils.CacheUtil
import com.neo.wanandroid.vm.HomeVM
import com.neo.wanandroid.vm.MineVM
import kotlinx.android.synthetic.main.fragment_mine.*
import okhttp3.internal.wait

class MineFragment : BaseVmDbFragment<MineVM, FragmentMineBinding>() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_mine
    }

    override fun init(savedInstanceState: Bundle?) {
        mDataBind.vm = mViewModel
        mDataBind.viewClick = this
    }

    override fun createObserver() {
        appVM.currentUser.apply {
            observe(this@MineFragment, { user ->
                mViewModel.apply {
                    if (user == null) {
                        username.set("请先登录")
                        info.set("id：--　排名：-")
                        logoutVisible.set(View.GONE)
                    } else {
                        username.set(if (user.nickname.isEmpty()) user.username else user.nickname)
                        info.set("id: ${value!!.id} 排名: 0")
                        logoutVisible.set(View.VISIBLE)
                    }
                }
            })
        }
    }

    fun onClick(view: View){
        when(view.id){
            R.id.logoutLL -> showMessage("是否确定退出", positiveAction = {
                CacheUtil.removeUser()
                appVM.currentUser.value = null
            }, negativeButtonText = "取消")
        }

    }

    fun login(){
        if(!CacheUtil.isLogin()){
            startActivity(Intent(activity, LoginActivity::class.java))
        }
    }

}