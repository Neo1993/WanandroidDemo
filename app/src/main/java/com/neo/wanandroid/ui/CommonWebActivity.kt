package com.neo.wanandroid.ui

import android.os.Bundle
import android.view.Menu
import com.neo.wanandroid.R
import com.neo.wanandroid.base.BaseVmDbActivity
import com.neo.wanandroid.databinding.ActivityCommonWebBinding
import com.neo.wanandroid.vm.CommonWebVM
import kotlinx.android.synthetic.main.include_toolbar.*

class CommonWebActivity : BaseVmDbActivity<CommonWebVM, ActivityCommonWebBinding>() {
    override fun getLayoutId(): Int {
        return R.layout.activity_common_web
    }

    override fun init(savedInstanceState: Bundle?) {
        setSupportActionBar(toolbar)
    }

    override fun createObserver() {
        TODO("Not yet implemented")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.web_menu, menu)
        return true
    }

}