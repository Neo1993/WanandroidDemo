package com.neo.wanandroid.ui.mine

import android.content.Intent
import android.os.Bundle
import com.neo.wanandroid.R
import com.neo.wanandroid.base.BaseVMFragment
import com.neo.wanandroid.vm.HomeVM
import kotlinx.android.synthetic.main.fragment_mine.*

class MineFragment : BaseVMFragment<HomeVM>() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_mine
    }

    override fun init(savedInstanceState: Bundle?) {
        loginTV.setOnClickListener {
            startActivity(Intent(activity, LoginActivity::class.java))
        }
    }

    override fun createObserver() {

    }
}