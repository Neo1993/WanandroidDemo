package com.neo.wanandroid.app.event

import com.kunminx.architecture.ui.callback.UnPeekLiveData
import com.lib.common.base.BaseViewModel
import com.neo.wanandroid.model.bean.CollectState

class EventVM: BaseViewModel() {
    var collectState: UnPeekLiveData<CollectState> = UnPeekLiveData()
}