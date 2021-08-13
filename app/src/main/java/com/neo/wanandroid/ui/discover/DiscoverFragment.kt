package com.neo.wanandroid.ui.discover

import android.os.Bundle
import com.neo.wanandroid.R
import com.neo.wanandroid.base.BaseVMFragment
import com.neo.wanandroid.vm.HomeVM

class DiscoverFragment : BaseVMFragment<HomeVM>() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_discover
    }

    override fun init(savedInstanceState: Bundle?) {

    }

    override fun createObserver() {

    }
}