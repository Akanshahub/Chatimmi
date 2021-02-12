package com.chatimmi.usermainfragment.group.filter.filtercategorygroup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.chatimmi.usermainfragment.group.immigration.GroupViewModel
import com.chatimmi.usermainfragment.group.immigration.ImmigrationGroupRepositary
import java.lang.IllegalArgumentException



class GroupFilterViewModelFactory (private val groupFilterRepository: GroupFilterRepository) : ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(FilterGroupViewModel::class.java)){
            return FilterGroupViewModel(groupFilterRepository) as T
        }else{
            throw IllegalArgumentException("Modal Class does not found")
        }
    }
}