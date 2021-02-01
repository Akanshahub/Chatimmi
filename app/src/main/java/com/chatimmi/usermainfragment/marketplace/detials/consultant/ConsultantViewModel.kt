package com.chatimmi.usermainfragment.marketplace.detials.consultant

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chatimmi.R
import com.chatimmi.app.utils.CommonTaskPerformer
import com.chatimmi.usermainfragment.marketplace.detials.school.SchoolAdapter

class ConsultantViewModel: ViewModel() {
    private lateinit var adapter: ConsultantAdapter
    var itemClickObserver = MutableLiveData<Boolean>()
    fun getAdapterClickObserver() = itemClickObserver as LiveData<Boolean>
    lateinit var commonTaskPerformer: CommonTaskPerformer
    fun init() {



        adapter =  ConsultantAdapter(R.layout.single_item_consultant, this)




    }


    fun getAdapter(): ConsultantAdapter? {
        return adapter
    }
}
