package com.chatimmi.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.chatimmi.repository.LogoutRepository
import com.chatimmi.repository.SignInRepository
import java.lang.IllegalArgumentException




@Suppress("UNCHECKED_CAST")
class LogoutViewModalFactory(private val logoutRepository: LogoutRepository) : ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(OtherFragmentViewModel::class.java)){
            return OtherFragmentViewModel(logoutRepository) as T
        }else{
            throw IllegalArgumentException("Modal Class does not found")
        }
    }
}