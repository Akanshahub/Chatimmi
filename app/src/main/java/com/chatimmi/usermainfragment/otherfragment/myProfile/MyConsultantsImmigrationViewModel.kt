package com.chatimmi.usermainfragment.otherfragment.myProfile

import android.widget.RadioGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chatimmi.R
import com.chatimmi.app.utils.CommonTaskPerformer
import java.util.*

class MyConsultantsImmigrationViewModel(private val myConsultantsRepository: MyConsultantsRepository) : ViewModel(), RadioGroup.OnCheckedChangeListener {
    lateinit var commonTaskPerformer: CommonTaskPerformer
    private lateinit var adapter: MyConsultantsImmigrationAdapter
    private lateinit var adapter1: MyConsultantsStudyAdapter
    private val list: ArrayList<GetProfileMyConsultantsResonse.Data.MyConsultantsList> = ArrayList()
    private val list1: ArrayList<GetProfileMyConsultantsResonse.Data.MyConsultantsList> = ArrayList()
    var itemClickObserver = MutableLiveData<Boolean>()
    fun getAdapterClickObserver() = itemClickObserver as LiveData<Boolean>
    fun init(commonTaskPerformer: CommonTaskPerformer) {
        this.commonTaskPerformer = commonTaskPerformer
        adapter = MyConsultantsImmigrationAdapter(R.layout.single_item_my_consultant,list)
        adapter1 = MyConsultantsStudyAdapter(R.layout.single_item_my_study, list)

        myConsultantsRepository.callListApi("2", "2")
    }

    fun connectedcheckboxOnClicked() {
        myConsultantsRepository.callListApi("2", "2")
    }

    fun checkboxOnClicked() {
        myConsultantsRepository.callListApi("2", "3")

    }

    fun getAdapter(): MyConsultantsImmigrationAdapter {
        return adapter
    }

    fun getAdapter1(): MyConsultantsStudyAdapter {
        return adapter1
    }

    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {


    }
}

