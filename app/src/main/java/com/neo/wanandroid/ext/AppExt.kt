package com.neo.wanandroid.ext

import androidx.appcompat.app.AppCompatActivity
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner

fun AppCompatActivity.showMessage(
    message: String,
    title: String = "温馨提示",
    positiveButtonText: String = "确定",
    positiveAction: () -> Unit = {},
    negativeButtonText: String = "",
    negativeAction: () -> Unit = {}
) {
    MaterialDialog(this)
        .cancelable(true)
        .lifecycleOwner(this)
        .show {
            title(text = title)
            message(text = message)
            positiveButton(text = positiveButtonText) {
                positiveAction.invoke()
            }
            negativeButton(text = negativeButtonText) {
                negativeAction.invoke()
            }
        }
}