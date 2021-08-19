package com.neo.wanandroid.ui.mine

import android.content.Intent
import android.os.Bundle
import android.widget.CompoundButton
import com.blankj.utilcode.util.ToastUtils
import com.neo.wanandroid.MainActivity
import com.neo.wanandroid.R
import com.neo.wanandroid.app.appVM
import com.neo.wanandroid.base.BaseVmDbActivity
import com.neo.wanandroid.databinding.ActivityLoginBinding
import com.neo.wanandroid.ext.hideSoftKeyboard
import com.neo.wanandroid.ext.showMessage
import com.neo.wanandroid.vm.LoginVM
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseVmDbActivity<LoginVM, ActivityLoginBinding>() {
    override fun init(savedInstanceState: Bundle?) {
        mDatabind.vm = mViewModel;
        mDatabind.viewClick = ViewClick()
    }

    override fun createObserver() {
        appVM.currentUser.observe(this) { user ->
            if (user != null) {
                ToastUtils.showShort("登录成功")
                finish()
            }
        }

    }

    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }

    inner class ViewClick{
        fun clear(){
            mViewModel.username.set("")
        }

        var onCheckChangeListener = CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            mViewModel.isShowPwd.set(isChecked)
        }

        fun login(){
            when{
                mViewModel.username.get().isNullOrEmpty() -> showMessage("请填写账号")
                mViewModel.password.get().isNullOrEmpty() -> showMessage("请填写密码")
                else -> mViewModel.login()
            }
        }

        fun goRegister(){
            hideSoftKeyboard(this@LoginActivity)
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }

    }

}