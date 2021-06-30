package com.neo.wanandroid.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.neo.wanandroid.ext.getVMClazz

abstract class BaseVMFragment<VM : BaseViewModel> : BaseFragment() {
    lateinit var mViewModel: VM

    abstract fun initView(savedInstanceState: Bundle?)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel = createViewModel()
        initView(savedInstanceState)
    }


    private fun createViewModel(): VM {
        return ViewModelProvider(this).get(getVMClazz(this))
    }
}