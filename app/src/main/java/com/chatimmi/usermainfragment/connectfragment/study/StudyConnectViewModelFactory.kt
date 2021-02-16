package com.chatimmi.usermainfragment.connectfragment.study

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.chatimmi.usermainfragment.connectfragment.immigrationconnect.ImmigrationConnectRepositary
import com.chatimmi.usermainfragment.group.immigration.ImmigrationGroupRepositary
import com.chatimmi.usermainfragment.group.study.StudyViewModel
import java.lang.IllegalArgumentException



class StudyConnectViewModelFactory (private val immigrationGroupRepositary: ImmigrationConnectRepositary) : ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(StudyConnectViewModel::class.java)){
            return StudyConnectViewModel(immigrationGroupRepositary) as T
        }else{
            throw IllegalArgumentException("Modal Class does not found")
        }
    }
}