package com.neo.wanandroid.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment(){
    lateinit var mFragmentView : View;
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mFragmentView = layoutInflater.inflate(getLayoutId(), container, false)
        return mFragmentView
    }

    abstract fun showLoading(loadingMessage : String  = "正在加载中")
    abstract fun dissmissLoading()
    @LayoutRes
    abstract fun getLayoutId() : Int

}