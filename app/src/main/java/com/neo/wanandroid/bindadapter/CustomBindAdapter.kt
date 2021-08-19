package com.neo.wanandroid.bindadapter

import android.text.InputType
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.EditText
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions

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

    @BindingAdapter(value = ["imageUrl"])
    @JvmStatic
    fun imageUrl(view: ImageView, url: String) {
        Glide.with(view.context)
                .load(url)
                .transition(DrawableTransitionOptions.withCrossFade(500))
                .into(view)
    }

    @JvmStatic
    @BindingAdapter(value = ["circleImageUrl"])
    fun circleImageUrl(view: ImageView, url: String){
        Glide.with(view.context)
                .load(url)
                .apply(RequestOptions.bitmapTransform(CircleCrop()))
                .transition(DrawableTransitionOptions.withCrossFade(500))
                .into(view)
    }

}
