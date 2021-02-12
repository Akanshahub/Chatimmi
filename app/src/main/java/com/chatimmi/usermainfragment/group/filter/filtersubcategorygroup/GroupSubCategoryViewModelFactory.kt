package com.chatimmi.usermainfragment.group.filter.filtersubcategorygroup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.chatimmi.usermainfragment.group.filter.filtercategorygroup.FilterGroupViewModel
import com.chatimmi.usermainfragment.group.filter.filtercategorygroup.GroupFilterRepository
import java.lang.IllegalArgumentException


class GroupSubCategoryViewModelFactory (private val groupFilterRepository: GroupFilterRepository) : ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(FilterSubCategoryGroupViewModel::class.java)){
            return FilterSubCategoryGroupViewModel(groupFilterRepository) as T
        }else{
            throw IllegalArgumentException("Modal Class does not found")
        }
    }
}