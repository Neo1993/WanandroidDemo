package com.neo.wanandroid.bindadapter

import android.text.InputType
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.EditText
import androidx.databinding.BindingAdapter

/**
 * 自定义 BindingAdapter
 */
object CustomBindAdapter {
    @JvmStatic
    @BindingAdapter(value = ["checkChange"])
    fun checkChange(checkBox: CheckBox, listener: CompoundButton.OnCheckedChangeListener) {
        checkBox.setOnCheckedChangeListener(listener)
    }

    @JvmStatic
    @BindingAdapter(value = ["showPwd"])
    fun showPwd(editText: EditText, showPwd: Boolean){
        if(showPwd) {
            editText.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
        }else{
            editText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        }
        editText.setSelection(editText.text.toString().trim().length)
    }

}
