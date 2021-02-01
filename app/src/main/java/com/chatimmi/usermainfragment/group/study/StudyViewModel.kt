package com.chatimmi.usermainfragment.group.study

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chatimmi.R
import com.chatimmi.app.utils.CommonTaskPerformer
import com.chatimmi.usermainfragment.group.immigration.details.ImmigrationDetailsActivity

class StudyViewModel : ViewModel(){

    private lateinit var adapter: StudyAdapter
    var itemClickObserver = MutableLiveData<Boolean>()
        fun getAdapterClickObserver() = itemClickObserver as LiveData<Boolean>
    lateinit var commonTaskPerformer: CommonTaskPerformer
    fun init(commonTaskPerformer: CommonTaskPerformer) {
        this.commonTaskPerformer = commonTaskPerformer;


        adapter = object : StudyAdapter(R.layout.single_item_study, this){
            override fun onitemBack() {
                commonTaskPerformer.performAction(ImmigrationDetailsActivity::class.java)

            }
            override fun onLockCallBack() {
                itemClickObserver.value=true
            }

        }
    }


        fun getAdapter(): StudyAdapter? {
            return adapter
        }
    }

 /*   private lateinit var adapter: StudyAdapter
    var itemClickObserver = MutableLiveData<Boolean>()
    fun getAdapterClickObserver() = itemClickObserver as LiveData<Boolean>


    //  private var viewModel: ImageViewModel? = null
    fun init() {
        adapter = StudyAdapter(R.layout.single_item_study, this)

    }
    fun getAdapter(): StudyAdapter? {
        return adapter
    }

}*/