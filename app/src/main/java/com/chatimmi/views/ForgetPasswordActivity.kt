package com.chatimmi.views

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.chatimmi.R
import com.chatimmi.app.utils.UIStateManager
import com.chatimmi.app.utils.showToast
import com.chatimmi.base.BaseActivitykt
import com.chatimmi.databinding.ActivityForgetPasswordBinding
import com.chatimmi.model.ResetPasswordResponse
import com.chatimmi.repository.ForgetPasswordRepository
import com.chatimmi.retrofitnetwork.ApiCallback
import com.chatimmi.viewmodel.ForgetPasswordViewModalFactory
import com.chatimmi.viewmodel.ForgetPasswordViewModel
import com.chatimmi.viewmodel.SignInViewModel


@Suppress("DEPRECATION")
class ForgetPasswordActivity : BaseActivitykt(),ApiCallback.ResetPasswordCallback {
    lateinit var binding: ActivityForgetPasswordBinding
    lateinit var viewModal: ForgetPasswordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_forget_password)
        val forgetPasswordRepository = ForgetPasswordRepository(this)
        val factory = ForgetPasswordViewModalFactory(forgetPasswordRepository)
        viewModal = ViewModelProviders.of(this, factory).get(ForgetPasswordViewModel::class.java)
        binding.forgetPasswordViewModel = viewModal

        binding.etEmail.addTextChangedListener(viewModal)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val decor = window.decorView
            decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }


        binding.backButton.setOnClickListener {

            val intent = Intent(this@ForgetPasswordActivity, SignInActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        }
        binding.etEmail.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int,
                                       count: Int) {
                if (s != "") {
                    if (validate()) {
                        binding.btnSignupRed.visibility = View.VISIBLE
                        binding.btnSignupGray.visibility = View.GONE
                    }
                }

            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int,
                                           after: Int) {
            }

            override fun afterTextChanged(s: Editable) {}
        })


        forgetPasswordRepository.getResetPasswordResponseData().observe(this, Observer {
            it?.let {
                when (it) {
                    is UIStateManager.Success<*> -> {
                        val getData = it.data as ResetPasswordResponse
                        Log.d("Results", "onCreate: $getData")
                        showToast(getData.message.toString())
                    }
                    is UIStateManager.Error -> {
                        showToast(it.msg)
                    }
                    is UIStateManager.Loading -> {
                        if (it.shouldShowLoading) {
                            showLoader()
                        } else {
                            hideLoader()
                        }

                    }
                    else -> {
                    }
                }
            }
        })
    }


    fun validate(): Boolean {
        Boolean
        val email = binding.etEmail.text.toString()
        if ("" == email) {
            binding.btnSignupRed.visibility = View.GONE
            binding.btnSignupGray.visibility = View.VISIBLE
            return false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.btnSignupRed.visibility = View.GONE
            binding.btnSignupGray.visibility = View.VISIBLE
            return false
        }
        return true
    }

    override fun onSuccessForgotPassword(resetPasswordResponse: ResetPasswordResponse) {

    }

    override fun onShowBaseLoader() {

    }

    override fun onHideBaseLoader() {

    }

    override fun onError(errorMessage: String) {
       toastMessage(errorMessage,this)
    }
}