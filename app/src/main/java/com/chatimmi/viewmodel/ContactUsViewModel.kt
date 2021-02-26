package com.chatimmi.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chatimmi.app.utils.UIStateManager
import com.chatimmi.repository.ContactUsRepository

class ContactUsViewModel(private val contactUsRepository: ContactUsRepository) : ViewModel() {
    var description = ""
    var tittle = ""

    private val validationObserver by lazy {
        MutableLiveData<UIStateManager>()

    }

    fun getValidationData() = validationObserver as LiveData<UIStateManager>
    fun submitOnClicked() {
        if (validate()) {
            contactUsRepository.callContactUsApi(tittle, description)
        }
    }

    private fun validate(): Boolean {
        if ("" == tittle) {
            validationObserver.value = UIStateManager.Error("Please enter title")

            return false
        }
        if (tittle.length < 3) {
            validationObserver.value = UIStateManager.Error("Title should not less than 3 characters")
            return false
        }
        if ("" == description) {
            validationObserver.value = UIStateManager.Error("Please enter description")
            return false
        }
        if (description.length < 3) {
            validationObserver.value = UIStateManager.Error("Description should not less than 3 characters")
            return false
        }

        return true
    }
}