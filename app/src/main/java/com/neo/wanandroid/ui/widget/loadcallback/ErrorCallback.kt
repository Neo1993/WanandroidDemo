package com.neo.wanandroid.ui.widget.loadcallback

import com.kingja.loadsir.callback.Callback
import com.neo.wanandroid.R

class ErrorCallback: Callback() {
    override fun onCreateView(): Int {
        return R.layout.layout_error
    }
}