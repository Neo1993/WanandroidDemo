package com.neo.wanandroid.ui.mine

import android.content.Intent
import android.os.Bundle
import com.neo.wanandroid.R
import com.neo.wanandroid.base.BaseVMFragment
import com.neo.wanandroid.base.BaseVmDbFragment
import com.neo.wanandroid.databinding.FragmentMineBinding
import com.neo.wanandroid.vm.HomeVM
import com.neo.wanandroid.vm.MineVM
import kotlinx.android.synthetic.main.fragment_mine.*

class MineFragment : BaseVmDbFragment<MineVM, FragmentMineBinding>() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_mine
    }

    override fun init(savedInstanceState: Bundle?) {
        loginTV.setOnClickListener {
            startActivity(Intent(activity, LoginActivity::class.java))
        }
    }

    override fun createObserver() {

    }
}