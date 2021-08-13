package com.neo.wanandroid.ui.system

import android.os.Bundle
import com.neo.wanandroid.R
import com.neo.wanandroid.base.BaseVMFragment
import com.neo.wanandroid.vm.HomeVM

class SystemFragment : BaseVMFragment<HomeVM>() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_system
    }

    override fun init(savedInstanceState: Bundle?) {

    }

    override fun createObserver() {

    }
}