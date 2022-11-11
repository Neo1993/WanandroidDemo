package com.neo.wanandroid.ui.fragment

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.lib.common.base.BaseVmDbFragment
import com.neo.wanandroid.R
import com.neo.wanandroid.databinding.FragmentSystemBinding
import com.neo.wanandroid.ext.init
import com.neo.wanandroid.vm.HomeVM
import com.neo.wanandroid.vm.SystemVM

class SystemFragment : BaseVmDbFragment<SystemVM, FragmentSystemBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        mDataBinding.refreshLayout.init({

        },{

        })
    }

    override fun initData() {

    }



}