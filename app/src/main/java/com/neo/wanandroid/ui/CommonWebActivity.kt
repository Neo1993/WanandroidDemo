package com.neo.wanandroid.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.LinearLayout
import com.just.agentweb.AgentWeb
import com.neo.wanandroid.R
import com.neo.wanandroid.base.BaseVmDbActivity
import com.neo.wanandroid.databinding.ActivityCommonWebBinding
import com.neo.wanandroid.ext.initClose
import com.neo.wanandroid.vm.CommonWebVM
import kotlinx.android.synthetic.main.activity_common_web.*
import kotlinx.android.synthetic.main.include_toolbar.*

class CommonWebActivity : BaseVmDbActivity<CommonWebVM, ActivityCommonWebBinding>() {
    lateinit var preWeb: AgentWeb.PreAgentWeb
    lateinit var mAgentWeb: AgentWeb

    companion object {
        fun go(context: Context, bundle: Bundle){
            val intent = Intent(context, CommonWebActivity::class.java)
            intent.putExtras(bundle)
            context.startActivity(intent)
        }

    }

    override fun getLayoutId(): Int {
        return R.layout.activity_common_web
    }

    override fun init(savedInstanceState: Bundle?) {
        setSupportActionBar(mDatabind.includeToolbar.toolbar)
        supportActionBar?.run {
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }
        mViewModel.showTitle = intent.extras?.getString("title").toString()
        mDatabind.includeToolbar.toolbar.initClose(mViewModel.showTitle){
            finish()
        }
        mViewModel.url = intent.extras?.getString("webUrl").toString()
        preWeb = AgentWeb.with(this)
                .setAgentWebParent(mDatabind.containerFL, LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
                .useDefaultIndicator()
                .createAgentWeb()
                .ready()

        mAgentWeb = preWeb.go(mViewModel.url)

    }

    override fun createObserver() {

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.web_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.web_share -> {
                //分享
                startActivity(Intent.createChooser(Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, "{${mViewModel.showTitle}}:${mViewModel.url}")
                    type = "text/plain"
                }, "分享到"))
            }
            R.id.web_refresh -> {
                //刷新网页
                mAgentWeb?.urlLoader?.reload()
            }
            R.id.web_collect -> {}
            R.id.web_liulanqi -> {
                //用浏览器打开
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(mViewModel.url)))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPause() {
        mAgentWeb?.webLifeCycle?.onPause()
        super.onPause()
    }

    override fun onResume() {
        mAgentWeb?.webLifeCycle?.onResume()
        super.onResume()
    }

    override fun onDestroy() {
        mAgentWeb?.webLifeCycle?.onDestroy()
//        setSupportActionBar(null)
        super.onDestroy()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if(mAgentWeb.handleKeyEvent(keyCode, event)){
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

}