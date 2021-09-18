package com.neo.wanandroid.ui.system

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.neo.wanandroid.R
import com.neo.wanandroid.base.BaseVMFragment
import com.neo.wanandroid.base.BaseVmDbFragment
import com.neo.wanandroid.databinding.FragmentSystemBinding
import com.neo.wanandroid.ext.init
import com.neo.wanandroid.vm.HomeVM
import com.neo.wanandroid.vm.SystemVM

class SystemFragment : BaseVmDbFragment<SystemVM, FragmentSystemBinding>() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_system
    }

    override fun init(savedInstanceState: Bundle?) {
        mDataBind.refreshLayout.init({

        },{

        })
    }

    override fun createObserver() {

    }



}