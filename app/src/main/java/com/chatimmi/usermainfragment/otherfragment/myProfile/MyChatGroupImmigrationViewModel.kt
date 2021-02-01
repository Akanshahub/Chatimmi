package com.chatimmi.usermainfragment.otherfragment.myProfile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chatimmi.R
import com.chatimmi.app.utils.CommonTaskPerformer

class MyChatGroupImmigrationViewModel : ViewModel(){
    lateinit var commonTaskPerformer: CommonTaskPerformer
    private lateinit var adapter: MyChatGroupImmigrationAdapter
    var itemClickObserver = MutableLiveData<Boolean>()
    fun getAdapterClickObserver() = itemClickObserver as LiveData<Boolean>
    fun init(commonTaskPerformer: CommonTaskPerformer) {
        this.commonTaskPerformer=commonTaskPerformer
        adapter = object : MyChatGroupImmigrationAdapter(R.layout.single_item_immigrationlist, this){

        }
    }
    fun getAdapter(): MyChatGroupImmigrationAdapter? {
        return adapter
    }
}


