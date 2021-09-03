package com.neo.wanandroid.app.event

import androidx.lifecycle.MutableLiveData
import com.neo.wanandroid.base.BaseViewModel
import com.neo.wanandroid.model.bean.CollectState

class EventVM: BaseViewModel() {
    var collectState: MutableLiveData<CollectState> = MutableLiveData()
}