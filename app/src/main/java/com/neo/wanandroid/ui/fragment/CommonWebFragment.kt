package com.neo.wanandroid.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.activity.addCallback
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.blankj.utilcode.util.VibrateUtils
import com.just.agentweb.AgentWeb
import com.neo.wanandroid.R
import com.neo.wanandroid.base.BaseFragment
import com.neo.wanandroid.databinding.FragmentCommonWebBinding
import com.neo.wanandroid.ext.initClose
import com.neo.wanandroid.vm.CommonWebVM
import com.neo.wanandroid.vm.RequestCollectVM

class CommonWebFragment : BaseFragment<CommonWebVM, FragmentCommonWebBinding>() {
    lateinit var preWeb: AgentWeb.PreAgentWeb
    lateinit var mAgentWeb: AgentWeb

    private val requestCollectVM: RequestCollectVM = RequestCollectVM()

    override fun initView(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        preWeb = AgentWeb.with(this)
            .setAgentWebParent(mDataBinding.containerFL, LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
            .useDefaultIndicator()
            .createAgentWeb()
            .ready()

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            mAgentWeb?.let { web ->
                if (web.webCreator.webView.canGoBack()) {
                    web.webCreator.webView.goBack()
                } else {
                    findNavController().navigateUp()
                }
            }
        }

    }

    override fun initData() {
        arguments?.run {
            mViewModel.let {
                it.showTitle = getString("showTitle") ?: ""
                it.url = getString("url") ?: ""
                mAgentWeb = preWeb.go(mViewModel.url)
            }
        }

        mDataBinding.includeToolbar.toolbar.initClose(mViewModel.showTitle){
            findNavController().navigateUp()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.web_menu, menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        if(mViewModel.isCollect){
            menu?.findItem(R.id.web_collect)?.icon = ContextCompat.getDrawable(mActivity, R.drawable.ic_collected)
        }else{
            menu?.findItem(R.id.web_collect)?.icon = ContextCompat.getDrawable(mActivity, R.drawable.ic_collect)
        }
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
            R.id.web_collect -> {
                //点击收藏 震动一下
                VibrateUtils.vibrate(40)

                if(mViewModel.isCollect){
                    requestCollectVM.uncollect(mViewModel.id)
                }else{
                    requestCollectVM.collect(mViewModel.id)
                }
            }
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
        super.onDestroy()
    }

}