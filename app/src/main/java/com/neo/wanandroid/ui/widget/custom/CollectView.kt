package com.neo.wanandroid.ui.widget.custom

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.blankj.utilcode.util.VibrateUtils
import com.neo.wanandroid.R
import com.neo.wanandroid.utils.CacheUtil
import per.goweii.reveallayout.RevealLayout

/**
 * @author CuiZhen
 */
class CollectView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : RevealLayout(context, attrs, defStyleAttr), View.OnTouchListener {

    private var onCollectViewClickListener: OnCollectViewClickListener? = null

    override fun initAttr(attrs: AttributeSet) {
        super.initAttr(attrs)
        setCheckWithExpand(true)
        setUncheckWithExpand(false)
        setCheckedLayoutId(R.layout.layout_collect_view_checked)
        setUncheckedLayoutId(R.layout.layout_collect_view_unchecked)
        setAnimDuration(300)
        setAllowRevert(true)
        setOnTouchListener(this)
    }

    private fun isValidClick(x: Float, y: Float): Boolean {
        return x >= 0 && x <= width && y >= 0 && y <= height
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        return false
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                return isValidClick(event.getX(), event.getY())
            }
            MotionEvent.ACTION_UP -> {
                //震动一下
                if (CacheUtil.isLogin(true)) {
                    //震动一下
                    VibrateUtils.vibrate(40)
                    onCollectViewClickListener?.onClick(this)
                    return true
                }
            }
        }
        return false
    }

    fun toggleState(){
        toggle()
    }

    fun setOnCollectViewClickListener(onCollectViewClickListener: OnCollectViewClickListener) {
        this.onCollectViewClickListener = onCollectViewClickListener
    }

    interface OnCollectViewClickListener {
        fun onClick(v: CollectView)
    }
}