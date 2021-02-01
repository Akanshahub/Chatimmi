package com.chatimmi.usermainfragment.marketplace.detials.school

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chatimmi.R
import com.chatimmi.app.utils.CommonTaskPerformer
import com.chatimmi.usermainfragment.group.immigration.details.ImmigrationDetailsActivity
import com.chatimmi.usermainfragment.group.study.StudyAdapter

class SchoolViewModel:ViewModel() {
    private lateinit var adapter: SchoolAdapter
    var itemClickObserver = MutableLiveData<Boolean>()
    fun getAdapterClickObserver() = itemClickObserver as LiveData<Boolean>
    lateinit var commonTaskPerformer: CommonTaskPerformer
    fun init() {



        adapter =  SchoolAdapter(R.layout.single_item_school, this)




    }


    fun getAdapter(): SchoolAdapter? {
        return adapter
    }
}
