package com.chatimmi.helper.joindailong

import androidx.lifecycle.ViewModel
import com.chatimmi.app.utils.CommonTaskPerformer
import com.chatimmi.usermainfragment.group.immigration.GroupListResponse


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
        joinRespository.callConnectGroupApi(modelObserver.groupID.toString())
        //commonTaskPerformer.dismissDialog()

    }

    fun updateValue() {
    }
}
