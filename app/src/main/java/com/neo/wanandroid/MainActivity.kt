package com.neo.wanandroid

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.navigation.findNavController
import com.blankj.utilcode.util.ToastUtils
import com.neo.wanandroid.base.BaseActivity
import com.neo.wanandroid.databinding.ActivityMainBinding
import com.neo.wanandroid.vm.MainVM

class MainActivity : BaseActivity<MainVM, ActivityMainBinding>() {
    private var exitTime = 0L
    override fun initView(savedInstanceState: Bundle?) {
        onBackPressedDispatcher.addCallback(this, object: OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                val nav = findNavController(R.id.host_fragment)
                if(nav.currentDestination != null && nav.currentDestination?.id != R.id.mainFragment) {
                    //当前页面不是主页,直接调用返回
                    nav.navigateUp()
                } else{
                    //是主页
                    if(System.currentTimeMillis() - exitTime > 2000L){
                        ToastUtils.showShort("再按一次退出程序")
                        exitTime = System.currentTimeMillis()
                    }else{
                        finish()
                    }
                }
            }

        })
    }

    override fun initData() {

    }

}