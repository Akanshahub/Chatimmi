package com.chatimmi.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.chatimmi.repository.ContactUsRepository


@Suppress("UNCHECKED_CAST")
class ContactUsViewModelFactory(private val contactUsRepository: ContactUsRepository) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ContactUsViewModel::class.java)) {
            return ContactUsViewModel(contactUsRepository) as T
        } else {
            throw IllegalArgumentException("Modal Class does not found")
        }
    }
}