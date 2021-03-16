package com.chatimmi.usermainfragment.otherfragment.myProfile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chatimmi.R
import com.chatimmi.app.utils.CommonTaskPerformer

class MyChatGroupImmigrationViewModel(private val myChatGroupViewModalRepository: MyChatGroupRepository) : ViewModel(){
    lateinit var commonTaskPerformer: CommonTaskPerformer
    private lateinit var adapter: MyChatGroupImmigrationAdapter

    private val list: ArrayList<GetProfileChatGroupResponse.Data.MyChatGroupList> = ArrayList()

    fun getAdapterClickObserver() = itemObserver as LiveData<GetProfileChatGroupResponse.Data.MyChatGroupList>
    var itemObserver = MutableLiveData<GetProfileChatGroupResponse.Data.MyChatGroupList>()
    fun init(commonTaskPerformer: CommonTaskPerformer) {
        this.commonTaskPerformer=commonTaskPerformer
        adapter = object : MyChatGroupImmigrationAdapter(R.layout.single_item_immigrationlist, list){
            override fun onCardCallBack(groupListItem: GetProfileChatGroupResponse.Data.MyChatGroupList) {
                itemObserver.value = groupListItem
            }

        }

        fetchImmiDetials()
    }
    fun fetchImmiDetials() {
        myChatGroupViewModalRepository.callGroupListApi("1", "1")
    }
    fun fetchStudyDetials() {
        myChatGroupViewModalRepository.callGroupListApi("1", "2")
    }

    fun getAdapter(): MyChatGroupImmigrationAdapter {
        return adapter
    }


}


