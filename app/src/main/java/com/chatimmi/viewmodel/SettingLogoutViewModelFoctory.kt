package com.chatimmi.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.chatimmi.repository.ChangePasswordRepository
import com.chatimmi.repository.LogoutRepository
import java.lang.IllegalArgumentException



@Suppress("UNCHECKED_CAST")
class SettingLogoutViewModelFoctory(private val logoutRepository: LogoutRepository) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingViewModel::class.java)) {
            return SettingViewModel(logoutRepository) as T
        } else {
            throw IllegalArgumentException("Modal Class does not found")
        }
    }
}