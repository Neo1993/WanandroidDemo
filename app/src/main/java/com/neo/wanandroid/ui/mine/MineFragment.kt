package com.neo.wanandroid.ui.mine

import android.os.Bundle
import com.neo.wanandroid.R
import com.neo.wanandroid.base.BaseFragment
import com.neo.wanandroid.base.BaseVMFragment
import com.neo.wanandroid.vm.RequestHomeViewModel
import kotlinx.android.synthetic.main.fragment_mine.*

class MineFragment : BaseVMFragment<RequestHomeViewModel>() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_mine
    }

    override fun initView(savedInstanceState: Bundle?) {
        loginTV.setOnClickListener {

        }
    }
}