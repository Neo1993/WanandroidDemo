package com.neo.wanandroid.ui.mine

import android.content.Intent
import android.os.Bundle
import android.widget.CompoundButton
import com.blankj.utilcode.util.ToastUtils
import com.neo.wanandroid.R
import com.neo.wanandroid.app.appVM
import com.neo.wanandroid.base.BaseActivity
import com.neo.wanandroid.databinding.ActivityLoginBinding
import com.neo.wanandroid.ext.hideSoftKeyboard
import com.neo.wanandroid.ext.initClose
import com.neo.wanandroid.ext.showMessage
import com.neo.wanandroid.vm.LoginVM

class LoginActivity : BaseActivity<LoginVM, ActivityLoginBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        mDataBinding.vm = mViewModel;
        mDataBinding.viewClick = ViewClick()
        mDataBinding.includeToolbar.toolbar.initClose("登录") {
            finish()
        }
    }

    override fun initData() {
        appVM.currentUser.observe(this) { user ->
            if (user != null) {
                ToastUtils.showShort("登录成功")
                finish()
            }
        }
    }

    inner class ViewClick {
        fun clear() {
            mViewModel.username.set("")
        }

        var onCheckChangeListener =
            CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
                mViewModel.isShowPwd.set(isChecked)
            }

        fun login() {
            when {
                mViewModel.username.get().isNullOrEmpty() -> showMessage("请填写账号")
                mViewModel.password.get().isNullOrEmpty() -> showMessage("请填写密码")
                else -> mViewModel.login()
            }
        }

        fun goRegister() {
            hideSoftKeyboard(this@LoginActivity)
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }

    }

}