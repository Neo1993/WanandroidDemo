package com.neo.wanandroid.base

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.neo.wanandroid.ext.dismissLoadingExt
import com.neo.wanandroid.ext.getVMClazz
import com.neo.wanandroid.ext.showLoadingExt


/**
 * MVVM项目中Activity的基类
 */
abstract class BaseVMActivity<VM : BaseViewModel> : BaseActivity() {
    /**
     * 是否需要使用DataBinding 供子类BaseVmDbActivity修改
     */
    private var isUseDB = false

    lateinit var mViewModel: VM

    /**
     * 界面初始化处理
     */
    abstract fun init(savedInstanceState: Bundle?)

    /**
     * 创建观察者
     */
    abstract fun createObserver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        if(isUseDB){
            initDataBind()
        }else{
            setContentView(getLayoutId())
        }
        mViewModel = createViewModel()
        addLoadingObserve()
        init(savedInstanceState)
        createObserver()
    }

    /**
     * 创建ViewModel
     */
    private fun createViewModel(): VM {
        return ViewModelProvider(this).get(getVMClazz(this))
    }

    /**
     * 添加加载弹窗观察者
     */
    private fun addLoadingObserve() {
        mViewModel.uiLoadingChange.showDialog.observe(this, Observer {
            showLoading(it)
        })

        mViewModel.uiLoadingChange.dismissDialog.observe(this, Observer {
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

    /**
     * 是否使用DataBinding
     */
    fun useDataBinding(isUseDB: Boolean){
        this.isUseDB = isUseDB
    }

    /**
     * 供子类BaseVmDbActivity 初始化Databinding操作
     */
    open fun initDataBind(){}
}