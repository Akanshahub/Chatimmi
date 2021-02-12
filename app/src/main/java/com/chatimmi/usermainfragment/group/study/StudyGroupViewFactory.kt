package com.chatimmi.usermainfragment.group.study

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.chatimmi.usermainfragment.group.immigration.GroupViewModel
import com.chatimmi.usermainfragment.group.immigration.ImmigrationGroupRepositary
import java.lang.IllegalArgumentException


class StudyGroupViewFactory (private val immigrationGroupRepositary: ImmigrationGroupRepositary) : ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(StudyViewModel::class.java)){
            return StudyViewModel(immigrationGroupRepositary) as T
        }else{
            throw IllegalArgumentException("Modal Class does not found")
        }
    }
}