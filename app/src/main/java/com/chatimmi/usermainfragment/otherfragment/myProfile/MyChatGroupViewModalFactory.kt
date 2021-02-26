package com.chatimmi.usermainfragment.otherfragment.myProfile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class MyChatGroupViewModalFactory (private val myChatGroupViewModalRepository: MyChatGroupRepository) : ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MyChatGroupImmigrationViewModel::class.java)){
            return MyChatGroupImmigrationViewModel(myChatGroupViewModalRepository) as T
        }else{
            throw IllegalArgumentException("Modal Class does not found")
        }
    }
}