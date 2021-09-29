package com.neo.wanandroid.vm

import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.VibrateUtils
import com.kunminx.architecture.ui.callback.UnPeekLiveData
import com.neo.wanandroid.app.eventVM
import com.neo.wanandroid.base.BaseViewModel
import com.neo.wanandroid.ext.request
import com.neo.wanandroid.model.bean.CollectState
import com.neo.wanandroid.network.apiService

class RequestCollectVM: BaseViewModel(){
    var collectState: MutableLiveData<CollectState> = MutableLiveData()

    /**
     * 收藏
     */
    fun collect(id: Int) {
        request({ apiService.collect(id) }, {
            collectState.value = CollectState(true, id, true)
            eventVM.collectState.value = CollectState(true, id, true)
        }, {
            collectState.value = CollectState(false, id, false, it.errorMsg)
            eventVM.collectState.value = CollectState(false, id, false, it.errorMsg)
        })
    }

    /**
     * 取消收藏
     */
    fun uncollect(id: Int) {
        request({ apiService.uncollect(id) }, {
            collectState.value = CollectState(true, id, false)
            eventVM.collectState.value = CollectState(true, id, false)
        }, {
            collectState.value = CollectState(false, id, true, it.errorMsg)
            eventVM.collectState.value = CollectState(false, id, true, it.errorMsg)
        })
    }
}