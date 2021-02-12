package com.chatimmi.helper.joindailong

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chatimmi.app.utils.CommonTaskPerformer
import com.chatimmi.usermainfragment.group.immigration.GroupListResponse
import java.util.*


class JoinViewModel(val joinRespository: JoinRespository) : ViewModel() {
    lateinit var commonTaskPerformer: CommonTaskPerformer

    private var modelObserver =GroupListResponse.Data.Group()

    fun init(commonTaskPerformer: CommonTaskPerformer,  group: GroupListResponse.Data.Group) {
        this.commonTaskPerformer = commonTaskPerformer
        this.modelObserver=group
    }

    fun onLaterClick() {
        commonTaskPerformer.dismissDialog()
    }

    fun onSubmitClick() {
        joinRespository.callConnectGroupApi(UUID.randomUUID().toString(), "jxchj", "2", TimeZone.getDefault().displayName,modelObserver.groupID.toString())
        //commonTaskPerformer.dismissDialog()

    }

    fun updateValue() {
    }
}
