package com.chatimmi.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.chatimmi.repository.SignInRepository
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class SignInViewModalFactory(private val signInRepository: SignInRepository) : ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SignInViewModel::class.java)){
            return SignInViewModel(signInRepository) as T
        }else{
            throw IllegalArgumentException("Modal Class does not found")
        }
    }
}