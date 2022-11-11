package com.neo.wanandroid.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.neo.wanandroid.R
import com.neo.wanandroid.base.BaseFragment
import com.neo.wanandroid.databinding.FragmentMainBinding
import com.neo.wanandroid.ui.fragment.DiscoverFragment
import com.neo.wanandroid.ui.fragment.GuideFragment
import com.neo.wanandroid.ui.fragment.HomeFragment
import com.neo.wanandroid.ui.fragment.MineFragment
import com.neo.wanandroid.ui.fragment.SystemFragment
import com.neo.wanandroid.vm.MainVM

class MainFragment : BaseFragment<MainVM, FragmentMainBinding>(){
    private lateinit var fragments : Map<Int, Fragment>
    lateinit var homeFragment: HomeFragment
    lateinit var systemFragment: SystemFragment
    lateinit var discoverFragment: DiscoverFragment
    lateinit var guideFragment: GuideFragment
    lateinit var mineFragment: MineFragment
    override fun initView(savedInstanceState: Bundle?) {
        homeFragment = HomeFragment::class.java.newInstance()
        systemFragment = SystemFragment::class.java.newInstance()
        discoverFragment = DiscoverFragment::class.java.newInstance()
        guideFragment = GuideFragment::class.java.newInstance()
        mineFragment = MineFragment::class.java.newInstance()

        fragments = mapOf(
            R.id.home to homeFragment,
            R.id.system to systemFragment,
            R.id.discovery to discoverFragment,
            R.id.navigation to guideFragment,
            R.id.mine to mineFragment
        )

        mDataBinding.bottomNavigationView.setOnNavigationItemSelectedListener{
            showFragment(it.itemId)
            true
        }

        showFragment(R.id.home)

    }

    override fun initData() {

    }

    fun showFragment(menuId : Int){
        val currentFragment = childFragmentManager.fragments.find {
            it.isVisible && it in fragments.values
        }
        val targetFragment = fragments[menuId]
        childFragmentManager.beginTransaction().apply {
            currentFragment?.let { if(it.isVisible) hide(it) }
            targetFragment?.let { if(it.isAdded) show(it) else add(R.id.containerFL, it)}
        }.commitAllowingStateLoss()
    }
}