package com.neo.wanandroid.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.launcher.ARouter

abstract class BaseFragment : Fragment(){
    lateinit var mFragmentView : View;

    @LayoutRes
    abstract fun getLayoutId() : Int
    abstract fun showLoading(loadingMessage : String  = "正在加载中")
    abstract fun dissmissLoading()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mFragmentView = layoutInflater.inflate(getLayoutId(), container, false)
        return mFragmentView
    }

    fun goActivity(path: String){
        ARouter.getInstance().build(path).navigation()
    }

    fun goActivity(path: String, bundle: Bundle){
        ARouter.getInstance().build(path).with(bundle).navigation();
    }

}