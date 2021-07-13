package com.neo.wanandroid.ui.widget.loadcallback

import android.content.Context
import android.view.View
import com.kingja.loadsir.callback.Callback
import com.neo.wanandroid.R

class LoadingCallback: Callback() {
    override fun onCreateView(): Int {
        return R.layout.layout_loading
    }

}