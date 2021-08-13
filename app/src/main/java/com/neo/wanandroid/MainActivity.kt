package com.neo.wanandroid

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.blankj.utilcode.util.ToastUtils
import com.neo.wanandroid.base.BaseVmDbActivity
import com.neo.wanandroid.databinding.ActivityMainBinding
import com.neo.wanandroid.ui.discover.DiscoverFragment
import com.neo.wanandroid.ui.guide.GuideFragment
import com.neo.wanandroid.ui.home.HomeFragment
import com.neo.wanandroid.ui.mine.MineFragment
import com.neo.wanandroid.ui.system.SystemFragment
import com.neo.wanandroid.vm.MainVM
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseVmDbActivity<MainVM, ActivityMainBinding>() {
    private var exitTime = 0L
    private lateinit var fragments : Map<Int, Fragment>
    lateinit var homeFragment: HomeFragment
    lateinit var systemFragment: SystemFragment
    lateinit var discoverFragment: DiscoverFragment
    lateinit var guideFragment: GuideFragment
    lateinit var mineFragment: MineFragment

    override fun init(savedInstanceState: Bundle?) {
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

        bottomNavigationView.setOnNavigationItemSelectedListener{
            showFragment(it.itemId)
            true
        }

        showFragment(R.id.home)

        onBackPressedDispatcher.addCallback(this, object: OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                if(System.currentTimeMillis() - exitTime > 2000L){
                    ToastUtils.showShort("再按一次退出程序")
                    exitTime = System.currentTimeMillis()
                }else{
                    finish()
                }
            }

        })
    }

    override fun createObserver() {

    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    fun showFragment(menuId : Int){
        val currentFragment = supportFragmentManager.fragments.find {
            it.isVisible && it in fragments.values
        }
        val targetFragment = fragments[menuId]
        supportFragmentManager.beginTransaction().apply {
            currentFragment?.let { if(it.isVisible) hide(it) }
            targetFragment?.let { if(it.isAdded) show(it) else add(R.id.containerFL, it)}
        }.commitAllowingStateLoss()
    }

}