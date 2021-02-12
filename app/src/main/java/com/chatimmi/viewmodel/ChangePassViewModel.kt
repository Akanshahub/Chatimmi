package com.chatimmi.viewmodel

import android.util.Patterns
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chatimmi.R
import com.chatimmi.app.utils.UIStateManager
import com.chatimmi.repository.ChangePasswordRepository
import com.chatimmi.repository.ForgetPasswordRepository
import java.util.*
import java.util.regex.Pattern

class ChangePassViewModel(private val changePasswordRepository: ChangePasswordRepository) : ViewModel() {
    var currentPass=""
    var newPass=""
    var confirmPass=""
    var PASSWORD_PATTERN_STRING = "^(?=.{8,})(?=.*[A-Z])(?=.*[@#\$%^&+*!=]).*\$"

    private val updateResponseObserver by lazy {
        MutableLiveData<UIStateManager>()

    }
    private val setPassResponseObserver by lazy {
        MutableLiveData<UIStateManager>()

    }
    private val validationObserver by lazy {
        MutableLiveData<UIStateManager>()

    }
    private val validationObserverOne by lazy {
        MutableLiveData<UIStateManager>()

    }
    fun updatePassOnClicked(){
        if(validate()){
            changePasswordRepository.callChangePasswordApi(UUID.randomUUID().toString(),"cdcxczxc", "2", TimeZone.getDefault().displayName,currentPass,newPass,confirmPass)
        }
        else{

        }
    }
    fun setPassOnClicked(){
        if(validate1()){
            changePasswordRepository.callSetPasswordApi(UUID.randomUUID().toString(),"cdcxczxc", "2", TimeZone.getDefault().displayName,newPass,confirmPass)
        }
        else{

        }
    }
    fun getValidationData() = validationObserver as LiveData<UIStateManager>
    fun getValidationDataOne() = validationObserverOne as LiveData<UIStateManager>
    fun getUpdateData() = updateResponseObserver as LiveData<UIStateManager>
    fun getSetPassData() = setPassResponseObserver as LiveData<UIStateManager>

    private fun validate(): Boolean {
        if ("" == currentPass) {
            validationObserver.value = UIStateManager.Error("Please enter your current password")

            return false
        }
        if (!Pattern.compile(PASSWORD_PATTERN_STRING).matcher(currentPass).matches()) {
            validationObserver.value = UIStateManager.Error("Password should be 8 characters long and contains one uppercase character, one special character and at least one number")
            return false
        }
        if ("" == newPass) {
            validationObserver.value = UIStateManager.Error("Please enter your new password")

            return false
        }
        if (!Pattern.compile(PASSWORD_PATTERN_STRING).matcher(newPass).matches()) {
            validationObserver.value = UIStateManager.Error("Password should be 8 characters long and contains one uppercase character, one special character and at least one number")
            return false
        }
        if ("" == confirmPass) {
            validationObserver.value = UIStateManager.Error("Please enter confirm password")

            return false
        }

        if (!Pattern.compile(PASSWORD_PATTERN_STRING).matcher(confirmPass).matches()) {
            validationObserver.value = UIStateManager.Error("Password should be 8 characters long and contains one uppercase character, one special character and at least one number")
            return false
        }

        if (newPass != confirmPass) {
            validationObserver.value = UIStateManager.Error("Password and confirm password does not match")

            return false
        }
        return true
    }

    private fun validate1(): Boolean {

        if ("" == newPass) {
            validationObserverOne.value = UIStateManager.Error("Please enter your new password")

            return false
        }
        if (!Pattern.compile(PASSWORD_PATTERN_STRING).matcher(newPass).matches()) {
            validationObserverOne.value = UIStateManager.Error("Password should be 8 characters long and contains one uppercase character, one special character and at least one number")
            return false
        }
        if ("" == confirmPass) {
            validationObserverOne.value = UIStateManager.Error("Please enter confirm password")

            return false
        }

        if (!Pattern.compile(PASSWORD_PATTERN_STRING).matcher(confirmPass).matches()) {
            validationObserverOne.value = UIStateManager.Error("Password should be 8 characters long and contains one uppercase character, one special character and at least one number")
            return false
        }

        if (newPass != confirmPass) {
            validationObserverOne.value = UIStateManager.Error("Password and confirm password does not match")

            return false
        }
        return true
    }
    fun logoutRequest(){
        changePasswordRepository.callLogoutApi(UUID.randomUUID().toString(),"hdjhfhdjh", "2", TimeZone.getDefault().displayName)
    }

}