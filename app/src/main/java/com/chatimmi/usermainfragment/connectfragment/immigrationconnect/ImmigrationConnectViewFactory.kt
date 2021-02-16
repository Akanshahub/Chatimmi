package com.chatimmi.usermainfragment.connectfragment.immigrationconnect

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException



class ImmigrationConnectViewFactory (private val immigrationConnectRepositary: ImmigrationConnectRepositary) : ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ImmigrationConnectViewModel::class.java)){
            return ImmigrationConnectViewModel(immigrationConnectRepositary) as T
        }else{
            throw IllegalArgumentException("Modal Class does not found")
        }
    }
}