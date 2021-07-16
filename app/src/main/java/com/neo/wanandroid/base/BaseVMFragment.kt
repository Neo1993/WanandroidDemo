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

    /**
     * 界面初始化处理
     */
    abstract fun init(savedInstanceState: Bundle?)

    /**
     * 创建观察者
     */
    abstract fun createObserver()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel = createViewModel()
        addLoadingObserve()
        init(savedInstanceState)
        createObserver()
    }

    /**
     * 创建ViewModel(泛型绑定)
     */
    private fun createViewModel(): VM {
        return ViewModelProvider(this).get(getVMClazz(this))
    }

    /**
     * 添加加载弹窗观察者
     */
    private fun addLoadingObserve() {
        mViewModel.uiLoadingChange.showDialog.observe(viewLifecycleOwner, Observer {
            showLoading(it)
        })

        mViewModel.uiLoadingChange.dismissDialog.observe(viewLifecycleOwner, Observer {
            dissmissLoading()
        })
    }

    /**
     * 显示加载弹窗
     */
    override fun showLoading(loadingMessage: String) {
        showLoadingExt(loadingMessage)
    }

    /**
     * 关闭加载弹窗
     */
    override fun dissmissLoading() {
        dismissLoadingExt()
    }

}