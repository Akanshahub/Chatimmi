package com.chatimmi.viewmodel

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chatimmi.app.utils.UIStateManager
import com.chatimmi.model.UserDetialResponse
import com.chatimmi.repository.SignInRepository
import java.util.*
import java.util.regex.Pattern


class SignInViewModel(val signupRepository: SignInRepository) :
        ViewModel() {

    private var signUpResponseLiveData: LiveData<UserDetialResponse>? = null
    var emailAddress = ""
    var password = ""


    private val validationObserver by lazy {
        MutableLiveData<UIStateManager>()
    }

    fun getValidationData() = validationObserver as LiveData<UIStateManager>


    fun signInOnClick() {
        if (validate()) {

            signupRepository.callLoginApi(UUID.randomUUID().toString(), "2", TimeZone.getDefault().displayName, emailAddress, password, "assdds", "1")
        }

    }


    private fun validate(): Boolean {
        if ("" == emailAddress) {
            validationObserver.value = UIStateManager.Error("Please enter your email")

            return false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()) {
            validationObserver.value = UIStateManager.Error("Email address is not valid, please provide a valid email")
            return false
        }
        if ("" == password) {
            validationObserver.value = UIStateManager.Error("Please enter your password")
            return false
        }
        return true
    }
}