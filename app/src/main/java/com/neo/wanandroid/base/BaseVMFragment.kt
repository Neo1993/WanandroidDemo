package com.neo.wanandroid.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.neo.wanandroid.ext.dismissLoadingExt
import com.neo.wanandroid.ext.getVMClazz
import com.neo.wanandroid.ext.showLoadingExt

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
        addLoadingObserve()
        initView(savedInstanceState)
    }


    private fun createViewModel(): VM {
        return ViewModelProvider(this).get(getVMClazz(this))
    }

    private fun addLoadingObserve() {
        mViewModel.uiLoadingChange.showDialog.observe(viewLifecycleOwner, Observer {
            showLoading(it)
        })

        mViewModel.uiLoadingChange.dismissDialog.observe(viewLifecycleOwner, Observer {
            dissmissLoading()
        })
    }

    override fun showLoading(loadingMessage: String) {
        showLoadingExt(loadingMessage)
    }

    override fun dissmissLoading() {
        dismissLoadingExt()
    }

}