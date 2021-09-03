package com.neo.wanandroid.vm

import com.blankj.utilcode.util.VibrateUtils
import com.neo.wanandroid.app.eventVM
import com.neo.wanandroid.base.BaseViewModel
import com.neo.wanandroid.ext.request
import com.neo.wanandroid.model.bean.CollectState
import com.neo.wanandroid.network.apiService

class RequestCollectVM: BaseViewModel(){

    /**
     * 收藏
     */
    fun collect(id: Int) {
        request({ apiService.collect(id) }, {
            eventVM.collectState.value = CollectState(true, id, true)
        }, {
            eventVM.collectState.value = CollectState(false, id, false, it.errorMsg)
        })
    }

    /**
     * 取消收藏
     */
    fun uncollect(id: Int) {
        request({ apiService.uncollect(id) }, {
            eventVM.collectState.value = CollectState(true, id, false)
        }, {
            eventVM.collectState.value = CollectState(false, id, true, it.errorMsg)
        })
    }
}