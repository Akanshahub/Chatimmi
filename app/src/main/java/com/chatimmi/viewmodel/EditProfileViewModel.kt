package com.chatimmi.viewmodel

import android.util.Patterns
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chatimmi.app.utils.UIStateManager

class EditProfileViewModel:ViewModel() {
    var emailAddress = ""
    var userName = ""

    private val updateResponseObserver by lazy {
        MutableLiveData<UIStateManager>()

    }
    private val validationObserver by lazy {
        MutableLiveData<UIStateManager>()

    }
    fun getValidationData() = validationObserver as LiveData<UIStateManager>
    fun getUpdateData() = updateResponseObserver as LiveData<UIStateManager>


    fun updateOnClicked() {
        if (validate()) {
            updateResponseObserver.value=UIStateManager.Success("updated")

        }else{
          //  updateResponseObserver.value=UIStateManager.Error("Failed")
        }

    }

    private fun validate(): Boolean {

        if ("" == userName) {
            validationObserver.value = UIStateManager.Error("Please enter your full name")
            return false
        }
        if (userName.length < 2) {
            validationObserver.value = UIStateManager.Error("Full name must be atleast 2 characters")
            return false
        }
        if ("" == emailAddress) {
            validationObserver.value = UIStateManager.Error("Please enter your email")

            return false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()) {
            validationObserver.value = UIStateManager.Error("Email address is not valid, please provide a valid email")
            return false
        }

        return true
    }
}