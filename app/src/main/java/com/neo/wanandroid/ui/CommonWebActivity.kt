package com.neo.wanandroid.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.VibrateUtils
import com.just.agentweb.AgentWeb
import com.neo.wanandroid.R
import com.neo.wanandroid.app.constant.PATH_ACTIVITY_COMMONWEB
import com.neo.wanandroid.app.eventVM
import com.neo.wanandroid.base.BaseVmDbActivity
import com.neo.wanandroid.databinding.ActivityCommonWebBinding
import com.neo.wanandroid.ext.initClose
import com.neo.wanandroid.ext.showMessage
import com.neo.wanandroid.vm.CommonWebVM
import com.neo.wanandroid.vm.RequestCollectVM
import kotlinx.android.synthetic.main.include_toolbar.*
import kotlinx.android.synthetic.main.include_toolbar.view.*

@Route(path = PATH_ACTIVITY_COMMONWEB)
class CommonWebActivity : BaseVmDbActivity<CommonWebVM, ActivityCommonWebBinding>() {
    lateinit var preWeb: AgentWeb.PreAgentWeb
    lateinit var mAgentWeb: AgentWeb

    private val requestCollectVM: RequestCollectVM = RequestCollectVM()

    @JvmField
    @Autowired
    var id: Int = 0
    @JvmField
    @Autowired
    var title: String = ""
    @JvmField
    @Autowired(name = "url")
    var webUrl: String = ""
    @JvmField
    @Autowired
    var isCollect: Boolean = false


    override fun getLayoutId(): Int {
        return R.layout.activity_common_web
    }

    override fun init(savedInstanceState: Bundle?) {
        setSupportActionBar(mDatabind.includeToolbar.toolbar)
        supportActionBar?.run {
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }

        mViewModel.let {
            it.id = id
            it.showTitle = title
            it.url = webUrl
            it.isCollect = isCollect

            window.invalidatePanelMenu(Window.FEATURE_OPTIONS_PANEL)
            invalidateOptionsMenu()
        }
        mDatabind.includeToolbar.toolbar.initClose(mViewModel.showTitle){
            finish()
        }
        preWeb = AgentWeb.with(this)
                .setAgentWebParent(mDatabind.containerFL, LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
                .useDefaultIndicator()
                .createAgentWeb()
                .ready()

        mAgentWeb = preWeb.go(mViewModel.url)

    }

    override fun createObserver() {
        requestCollectVM.apply {
            collectState.observe(this@CommonWebActivity){
                if(!it.isSuccess){
                    showMessage(it.errorMsg)
                }
            }
        }

        eventVM.apply {
            collectState.observeInActivity(this@CommonWebActivity) {
                if (it.isSuccess) {
                    mViewModel.isCollect = it.isCollect;
                    window.invalidatePanelMenu(Window.FEATURE_OPTIONS_PANEL)
                    invalidateOptionsMenu()
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.web_menu, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        if(mViewModel.isCollect){
            menu?.findItem(R.id.web_collect)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_collected)
        }else{
            menu?.findItem(R.id.web_collect)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_collect)
        }
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.web_share -> {
                //??????
                startActivity(Intent.createChooser(Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, "{${mViewModel.showTitle}}:${mViewModel.url}")
                    type = "text/plain"
                }, "?????????"))
            }
            R.id.web_refresh -> {
                //????????????
                mAgentWeb?.urlLoader?.reload()
            }
            R.id.web_collect -> {
                //???????????? ????????????
                VibrateUtils.vibrate(40)

                if(mViewModel.isCollect){
                    requestCollectVM.uncollect(mViewModel.id)
                }else{
                    requestCollectVM.collect(mViewModel.id)
                }
            }
            R.id.web_liulanqi -> {
                //??????????????????
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