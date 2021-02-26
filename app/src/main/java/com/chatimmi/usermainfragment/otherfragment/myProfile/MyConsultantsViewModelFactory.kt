package com.chatimmi.usermainfragment.otherfragment.myProfile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider



class MyConsultantsViewModelFactory (private val myConsultantsRepository: MyConsultantsRepository) : ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MyConsultantsImmigrationViewModel::class.java)){
            return MyConsultantsImmigrationViewModel(myConsultantsRepository) as T
        }else{
            throw IllegalArgumentException("Modal Class does not found")
        }
    }
}