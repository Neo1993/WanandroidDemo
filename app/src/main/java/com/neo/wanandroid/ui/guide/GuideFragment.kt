package com.neo.wanandroid.ui.guide

import android.os.Bundle
import com.neo.wanandroid.R
import com.neo.wanandroid.base.BaseFragment
import com.neo.wanandroid.base.BaseVMFragment
import com.neo.wanandroid.vm.RequestHomeViewModel

class GuideFragment : BaseVMFragment<RequestHomeViewModel>() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_guide
    }

    override fun initView(savedInstanceState: Bundle?) {
        TODO("Not yet implemented")
    }
}