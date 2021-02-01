package com.chatimmi.viewmodel

import android.util.Patterns
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chatimmi.R
import com.chatimmi.app.utils.UIStateManager

class ChangePassViewModel : ViewModel() {
    var currentPass=""
    var newPass=""
    var confirmPass=""

    private val updateResponseObserver by lazy {
        MutableLiveData<UIStateManager>()

    }
    private val validationObserver by lazy {
        MutableLiveData<UIStateManager>()

    }
    fun updatePassOnClicked(){
        if(validate()){
            updateResponseObserver.value=UIStateManager.Success("updated")
        }
        else{

        }
    }
    fun getValidationData() = validationObserver as LiveData<UIStateManager>
    fun getUpdateData() = updateResponseObserver as LiveData<UIStateManager>

    private fun validate(): Boolean {
        if ("" == currentPass) {
            validationObserver.value = UIStateManager.Error("Please enter your old password")

            return false
        }
        if (currentPass.length<6){
            validationObserver.value = UIStateManager.Error("Old password should not less than 6 characters")

            return false
        }
        if ("" == newPass) {
            validationObserver.value = UIStateManager.Error("Please enter your new password")

            return false
        }
        if (newPass.length<6) {
            validationObserver.value = UIStateManager.Error("New password should not less than 6 characters")

            return false
        }
        if ("" == confirmPass) {
            validationObserver.value = UIStateManager.Error("Please enter confirm password")

            return false
        }
        if (confirmPass.length<6) {
            validationObserver.value = UIStateManager.Error("Confirm password should not less than 6 characters")

            return false
        }

        if (newPass != confirmPass) {
            validationObserver.value = UIStateManager.Error("Password and confirm password does not match")

            return false
        }
        return true
    }


}