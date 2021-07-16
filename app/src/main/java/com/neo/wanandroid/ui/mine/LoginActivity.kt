package com.neo.wanandroid.ui.mine

import android.os.Bundle
import com.neo.wanandroid.R
import com.neo.wanandroid.base.BaseVMActivity
import com.neo.wanandroid.vm.RequestLoginVM

class LoginActivity : BaseVMActivity<RequestLoginVM>() {
    override fun init(savedInstanceState: Bundle?) {

    }

    override fun createObserver() {

    }

    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }
}