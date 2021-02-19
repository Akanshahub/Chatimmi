package com.chatimmi.viewmodel

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chatimmi.model.ResetPasswordResponse
import com.chatimmi.repository.ForgetPasswordRepository


class ForgetPasswordViewModel(val forgetPasswordRepository: ForgetPasswordRepository) :
        ViewModel(), TextWatcher {
    private var resetPasswordResponse: LiveData<ResetPasswordResponse?>? = null
    public var activeDeactiveButtonObserver = MutableLiveData<Boolean>()
    var emailAddress = ""

    fun submitInOnClick() {

                forgetPasswordRepository.callResetPasswordApi(emailAddress)


    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        Log.d("Result", "onTextChanged: ${s}")

        s?.let {
            if (it.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(s).matches()) {
                Log.d("Message", "MATCHED: ${s}")
                activeDeactiveButtonObserver.value = true

            }else{
                activeDeactiveButtonObserver.value = false

            }
        }

    }
    override fun afterTextChanged(s: Editable?) {

    }


}

