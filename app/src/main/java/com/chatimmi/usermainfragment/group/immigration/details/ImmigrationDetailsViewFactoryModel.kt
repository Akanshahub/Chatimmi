package com.chatimmi.usermainfragment.group.immigration.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.chatimmi.usermainfragment.group.immigration.GroupViewModel
import com.chatimmi.usermainfragment.group.immigration.ImmigrationGroupRepositary
import java.lang.IllegalArgumentException



class ImmigrationDetailsViewFactoryModel (private val immigrationDetailsRepositary: ImmigrationDetailsRepositary) : ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ImmigrationDetailsViewModel::class.java)){
            return ImmigrationDetailsViewModel(immigrationDetailsRepositary) as T
        }else{
            throw IllegalArgumentException("Modal Class does not found")
        }
    }
}