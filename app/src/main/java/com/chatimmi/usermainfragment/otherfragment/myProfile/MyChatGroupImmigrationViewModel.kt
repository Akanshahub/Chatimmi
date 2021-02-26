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
    var itemClickObserver = MutableLiveData<Boolean>()
    fun getAdapterClickObserver() = itemClickObserver as LiveData<Boolean>
    fun init(commonTaskPerformer: CommonTaskPerformer) {
        this.commonTaskPerformer=commonTaskPerformer
        adapter = object : MyChatGroupImmigrationAdapter(R.layout.single_item_immigrationlist, list){

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


