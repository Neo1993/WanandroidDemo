package com.neo.wanandroid.ui.mine

import android.os.Bundle
import android.widget.CompoundButton
import com.neo.wanandroid.R
import com.neo.wanandroid.base.BaseVMActivity
import com.neo.wanandroid.base.BaseVmDbActivity
import com.neo.wanandroid.databinding.ActivityLoginBinding
import com.neo.wanandroid.ext.showMessage
import com.neo.wanandroid.vm.RequestLoginVM
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseVmDbActivity<RequestLoginVM, ActivityLoginBinding>() {
    override fun init(savedInstanceState: Bundle?) {
        mDatabind.vm = mViewModel;
        mDatabind.viewClick = ViewClick()
    }

    override fun createObserver() {

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
                else -> mViewModel.login(mViewModel.username.get().toString(), mViewModel.password.get().toString())
            }

            if(mViewModel.username.get().isNullOrEmpty() ) {
                showMessage("请填写账号")
            }

        }


    }

}