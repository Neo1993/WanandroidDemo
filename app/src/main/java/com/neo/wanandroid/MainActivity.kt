package com.neo.wanandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.neo.wanandroid.ui.discover.DiscoverFragment
import com.neo.wanandroid.ui.guide.GuideFragment
import com.neo.wanandroid.ui.home.HomeFragment
import com.neo.wanandroid.ui.mine.MineFragment
import com.neo.wanandroid.ui.system.SystemFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var fragments : Map<Int, Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView(){
        fragments = mapOf(
                R.id.home to HomeFragment::class.java.newInstance(),
                R.id.system to SystemFragment::class.java.newInstance(),
                R.id.discovery to DiscoverFragment::class.java.newInstance(),
                R.id.navigation to GuideFragment::class.java.newInstance(),
                R.id.mine to MineFragment::class.java.newInstance()
        )

//        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        bottomNavigationView.setOnNavigationItemSelectedListener{
            showFragment(it.itemId)
            true
        }

        showFragment(R.id.home)
    }


//    val mOnNavigationItemSelectedListener : BottomNavigationView.OnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener{
//        showFragment(it.itemId)
//        true
//    }

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