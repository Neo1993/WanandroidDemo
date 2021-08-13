package com.neo.wanandroid.ext

import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.neo.wanandroid.R
import kotlinx.android.synthetic.main.layout_loading_dialog_view.*

var loadingMsg = "请求网络中"
private var loadingDialog : MaterialDialog? = null

/**
 * 打开加载弹窗
 * @param message 弹窗提示
 */
fun AppCompatActivity.showLoadingExt(message: String = loadingMsg){
    if(!isFinishing){
        if(loadingDialog == null){
            loadingDialog = MaterialDialog(this)
                .cancelable(true)
                .cancelOnTouchOutside(false)
                .cornerRadius(12f)
                .customView(R.layout.layout_loading_dialog_view)
                .lifecycleOwner(this)
        }
        loadingDialog?.getCustomView()?.run {
            findViewById<TextView>(R.id.messageTV).text = message
        }
    }
    loadingDialog?.show()
}

/**
 * 打开加载弹窗
 * @param message 弹窗提示
 */
fun Fragment.showLoadingExt(message: String = loadingMsg){
    activity?.let {
        if(!it.isFinishing){
            if(loadingDialog == null){
                loadingDialog = MaterialDialog(it)
                    .cancelable(true)
                    .cancelOnTouchOutside(false)
                    .cornerRadius(12f)
                    .customView(R.layout.layout_loading_dialog_view)
                    .lifecycleOwner(this)
            }
            loadingDialog?.getCustomView()?.run {
                findViewById<TextView>(R.id.messageTV).text = message
            }
        }
        loadingDialog?.show()
    }
}

/**
 * 关闭加载弹窗
 */
fun AppCompatActivity.dismissLoadingExt() {
    loadingDialog?.dismiss()
    loadingDialog = null
}

/**
 * 关闭加载弹窗
 */
fun Fragment.dismissLoadingExt() {
    loadingDialog?.dismiss()
    loadingDialog = null
}