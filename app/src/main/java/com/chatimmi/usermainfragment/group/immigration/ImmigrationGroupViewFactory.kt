package com.chatimmi.usermainfragment.group.immigration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException



class ImmigrationGroupViewFactory (private val immigrationGroupRepositary: ImmigrationGroupRepositary) : ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(GroupViewModel::class.java)){
            return GroupViewModel(immigrationGroupRepositary) as T
        }else{
            throw IllegalArgumentException("Modal Class does not found")
        }
    }
}