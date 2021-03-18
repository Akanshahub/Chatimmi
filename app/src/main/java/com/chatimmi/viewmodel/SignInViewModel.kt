package com.chatimmi.viewmodel

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chatimmi.app.utils.UIStateManager
import com.chatimmi.repository.SignInRepository


class SignInViewModel(val signupRepository: SignInRepository) :
        ViewModel() {


    var emailAddress = ""
    var password = ""


    private val validationObserver by lazy {
        MutableLiveData<UIStateManager>()
    }

    fun getValidationData() = validationObserver as LiveData<UIStateManager>


    fun signInOnClick() {
        if (validate()) {
            signupRepository.callLoginApi(emailAddress, password, "assdds", "1")
        }

    }


    private fun validate(): Boolean {
        if ("" == emailAddress) {
            validationObserver.value = UIStateManager.Error("email")
            return false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()) {
            validationObserver.value = UIStateManager.Error("valid email")
            return false
        }
        if ("" == password) {
            validationObserver.value = UIStateManager.Error("password")
            return false
        }
        return true
    }
}