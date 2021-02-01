package com.chatimmi.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.chatimmi.repository.SignInRepository
import com.chatimmi.repository.SignUpRepository
import java.lang.IllegalArgumentException

class SignUpViewModalFactory (private val signUpRepository: SignUpRepository) : ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SignUpViewModel::class.java)){
            return SignUpViewModel(signUpRepository) as T
        }else{
            throw IllegalArgumentException("Modal Class does not found")
        }
    }
}