package com.neo.wanandroid.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.Observer
import com.neo.wanandroid.R
import com.neo.wanandroid.base.BaseFragment
import com.neo.wanandroid.base.BaseVMFragment
import com.neo.wanandroid.ext.handleCallback
import com.neo.wanandroid.vm.RequestHomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseVMFragment<RequestHomeViewModel>() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun initView(savedInstanceState: Bundle?) {
        mViewModel.getBanner()
        createObserver()
    }

    fun createObserver() {
        mViewModel.run {
            //监听轮播图请求的数据变化
            bannerData.observe(viewLifecycleOwner, Observer {
                handleCallback(it, {dataList ->
                    if(recyclerView.headerCount == 0){
                        val headView = LayoutInflater.from(context).inflate(R.layout.include_banner,null).apply{

                        }
                    }
                }, {e ->
                    e.printStackTrace()
                })

            })
        }
    }

}