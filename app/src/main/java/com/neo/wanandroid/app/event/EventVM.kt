package com.neo.wanandroid.app.event

import androidx.lifecycle.MutableLiveData
import com.kunminx.architecture.ui.callback.UnPeekLiveData
import com.neo.wanandroid.base.BaseViewModel
import com.neo.wanandroid.model.bean.CollectState

class EventVM: BaseViewModel() {
    var collectState: UnPeekLiveData<CollectState> = UnPeekLiveData()
}