package com.chatimmi.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.chatimmi.repository.ForgetPasswordRepository
import com.chatimmi.repository.SignInRepository
import java.lang.IllegalArgumentException



    @Suppress("UNCHECKED_CAST")
    class ForgetPasswordViewModalFactory(private val forgetPasswordRepository:ForgetPasswordRepository) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ForgetPasswordViewModel::class.java)) {
                return ForgetPasswordViewModel(forgetPasswordRepository) as T
            } else {
                throw IllegalArgumentException("Modal Class does not found")
            }
        }
    }

