package com.neo.wanandroid.ui.guide

import android.os.Bundle
import com.neo.wanandroid.R
import com.neo.wanandroid.base.BaseVMFragment
import com.neo.wanandroid.vm.HomeVM

class GuideFragment : BaseVMFragment<HomeVM>() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_guide
    }

    override fun init(savedInstanceState: Bundle?) {

    }

    override fun createObserver() {

    }
}