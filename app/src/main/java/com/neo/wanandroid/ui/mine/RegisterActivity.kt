package com.neo.wanandroid.ui.mine

import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import com.blankj.utilcode.util.ToastUtils
import com.lib.common.base.BaseVmDbActivity
import com.neo.wanandroid.app.appVM
import com.neo.wanandroid.databinding.ActivityRegisterBinding
import com.neo.wanandroid.ext.showMessage
import com.neo.wanandroid.vm.RegisterVM

class RegisterActivity : BaseVmDbActivity<RegisterVM, ActivityRegisterBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        mDataBinding.vm = mViewModel;
        mDataBinding.viewClick = ViewClick()
    }

    override fun initData() {
        mViewModel.regisetResult.observe(this){
            mViewModel.login()
        }

        appVM.currentUser.observe(this) { user ->
            if (user != null) {
                ToastUtils.showShort("注册成功")
                finish()
            }
        }

    }

    inner class ViewClick{
        fun clear(){
            mViewModel.username.set("")
        }

        var onCheckChangeListener = CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            mViewModel.isShowPwd.set(isChecked)
        }

        var onCheckChangeListener2 = CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            mViewModel.isShowPwd2.set(isChecked)
        }

        fun register(){
            when{
                mViewModel.username.get().isNullOrEmpty() -> showMessage("请填写账号")
                mViewModel.password.get().isNullOrEmpty() -> showMessage("请填写密码")
                mViewModel.repassword.get().isNullOrEmpty() -> showMessage("请填写确认密码")
                mViewModel.password.get().toString().length < 6 -> showMessage("密码最少6位")
                mViewModel.password.get() != mViewModel.repassword.get() -> showMessage("密码不一致")
                else -> mViewModel.register()
            }
        }

    }


}