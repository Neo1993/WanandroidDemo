package com.neo.wanandroid.app.bindadapter

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.neo.wanandroid.ext.toHtml

/**
 * 自定义BindAdapter
 */
object CustomBindAdapter {
    @BindingAdapter(value = ["textToHtml"])
    @JvmStatic
    fun textToHtml(textView: TextView, text: String) {
        textView.setText(text.toHtml())
    }

    @BindingAdapter(value = ["imageUrl"])
    @JvmStatic
    fun imageUrl(view: ImageView, url: String) {
        Glide.with(view.context.applicationContext)
            .load(url)
            .transition(DrawableTransitionOptions.withCrossFade(500))
            .into(view)
    }

}