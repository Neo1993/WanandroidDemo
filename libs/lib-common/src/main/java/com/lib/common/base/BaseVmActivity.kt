package com.lib.common.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.lib.common.ext.getVmClazz
import com.lib.common.ext.notNull

/**
 * ViewModelActivity基类,自动注入ViewModel
 */
abstract class BaseVmActivity<VM : BaseViewModel> : AppCompatActivity() {
    lateinit var mViewModel: VM

    abstract fun getLayoutId(): Int
    abstract fun initView(savedInstanceState: Bundle?)
    abstract fun initData()
    abstract fun initDataBinding(): View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDataBinding().notNull({
            setContentView(it)
        }, {
            setContentView(getLayoutId())
        })
        init(savedInstanceState)
    }

    private fun init(savedInstanceState: Bundle?) {
        mViewModel = createViewModel()
        initView(savedInstanceState)
        initData()
    }

    /**
     * 创建ViewModel
     */
    private fun createViewModel(): VM {
        return ViewModelProvider(this).get(getVmClazz(this))
    }


}