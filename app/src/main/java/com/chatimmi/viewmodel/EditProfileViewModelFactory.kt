package com.chatimmi.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.chatimmi.repository.ChangePasswordRepository
import com.chatimmi.repository.EditProfileRepository


@Suppress("UNCHECKED_CAST")
class EditProfileViewModelFactory(private val editProfileRepository: EditProfileRepository) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditProfileViewModel::class.java)) {
            return EditProfileViewModel(editProfileRepository) as T
        } else {
            throw IllegalArgumentException("Modal Class does not found")
        }
    }
}