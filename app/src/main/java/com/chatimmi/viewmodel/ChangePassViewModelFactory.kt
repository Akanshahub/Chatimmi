package com.chatimmi.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.chatimmi.repository.ChangePasswordRepository
import com.chatimmi.repository.ForgetPasswordRepository
import java.lang.IllegalArgumentException



@Suppress("UNCHECKED_CAST")
class ChangePassViewModelFactory(private val changePasswordRepository: ChangePasswordRepository) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChangePassViewModel::class.java)) {
            return ChangePassViewModel(changePasswordRepository) as T
        } else {
            throw IllegalArgumentException("Modal Class does not found")
        }
    }
}