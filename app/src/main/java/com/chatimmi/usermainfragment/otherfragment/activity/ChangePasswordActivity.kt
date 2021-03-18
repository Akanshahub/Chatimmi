package com.chatimmi.usermainfragment.otherfragment.activity

import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.chatimmi.R
import com.chatimmi.app.pref.Session
import com.chatimmi.app.utils.UIStateManager
import com.chatimmi.app.utils.showToast
import com.chatimmi.base.BaseActivitykt
import com.chatimmi.databinding.ActivityChangePasswordBinding
import com.chatimmi.model.ChangePasswordResponse
import com.chatimmi.model.LogoutResponse
import com.chatimmi.repository.ChangePasswordRepository
import com.chatimmi.retrofitnetwork.ApiCallback
import com.chatimmi.viewmodel.ChangePassViewModel
import com.chatimmi.viewmodel.ChangePassViewModelFactory

@Suppress("DEPRECATION")
class ChangePasswordActivity : BaseActivitykt(),ApiCallback.ChangePasswordCallBack,ApiCallback.LogoutCallback,ApiCallback.SetPasswordCallBack {

    lateinit var binding: ActivityChangePasswordBinding
    lateinit var viewModel: ChangePassViewModel
    lateinit var session: Session
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_change_password)
        val changePasswordRepository = ChangePasswordRepository(this,this,this,this)
        val factory = ChangePassViewModelFactory(changePasswordRepository)

        viewModel = ViewModelProviders.of(this, factory).get(ChangePassViewModel::class.java)
        binding.lifecycleOwner = this
        binding.changePassViewModel = viewModel
        session = Session(activity)
        binding.appBar.ivBack.setOnClickListener {
            onBackPressed()
        }

        if (session.getUserData()!!.data!!.user_details.password != null && !session.getUserData()!!.data!!.user_details.password!!.isEmpty()) {
            binding.text.text = getString(R.string.create_a_new_password)
            binding.tvCurrentPass.visibility=View.VISIBLE
            binding.etCurrentPass.visibility=View.VISIBLE
            binding.btnSignupRed.visibility=View.VISIBLE
            binding.btnSetPass.visibility=View.GONE
            binding.appBar.tvTitle11.text = getString(R.string.change_password)
        } else {
            binding.text.text = getString(R.string.set_a_new_password)
            binding.tvCurrentPass.visibility=View.GONE
            binding.etCurrentPass.visibility=View.GONE
            binding.btnSetPass.visibility=View.VISIBLE
            binding.btnSignupRed.visibility=View.GONE
            binding.appBar.tvTitle11.text = getString(R.string.set_password)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val decor = window.decorView
            decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        changePasswordRepository.getChangePasswordResponseData().observeForever {
            it?.let {
                when (it) {
                    is UIStateManager.Success<*> -> {
                        val getData = it.data as ChangePasswordResponse
                        alertDailog()
                        Log.d("Results", "onCreate: $getData")
                        //showToast(getData.message.toString())
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
                    is UIStateManager.ErrorCode ->{
                        callLogoutApi()
                    }
                    else -> {
                    }
                }
            }
        }
        changePasswordRepository.getSetPasswordResponseData().observeForever {
            it?.let {
                when (it) {
                    is UIStateManager.Success<*> -> {
                        val getData = it.data as ChangePasswordResponse
                        alertDailogSetPassword()
                        Log.d("Results", "onCreate: $getData")
                        //showToast(getData.message.toString())
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
        }
        viewModel.getValidationData().observe(this, Observer {
            when (it) {
                is UIStateManager.Error -> {
                    showToast(it.msg)
                }
                else -> {
                }
            }

        })
        viewModel.getValidationDataOne().observe(this, Observer {
            when (it) {
                is UIStateManager.Error -> {
                    showToast(it.msg)
                }
                else -> {
                }
            }

        })
        viewModel.getSetPassData().observe(this, Observer {
            it?.let {
                when (it) {
                    is UIStateManager.Success<*> -> {
                        val getData = it.data as ChangePasswordResponse
                        alertDailogSetPassword()
                        //Log.d("fabbb", "onCreate: $getData")
                    }

                    is UIStateManager.Error -> {
                        toastMessage(it.msg, this)
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
        changePasswordRepository.getLogOutResponseData().observe(this, androidx.lifecycle.Observer {
            it?.let {
                when (it) {
                    is UIStateManager.Success<*> -> {
                        val getData = it.data as LogoutResponse
                        Log.d("fabbb", "onCreate: $getData")
                    }

                    is UIStateManager.Error -> {
                        toastMessage(it.msg, this)
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

    fun alertDailog() {
        val mAlertDialog = AlertDialog.Builder(this)
        mAlertDialog.setTitle("Alert") //set alertdialog title
        mAlertDialog.setMessage(getString(R.string.you_have_changed_your_password)) //set alertdialog message
        mAlertDialog.setPositiveButton("Okay") { dialog, id ->

            viewModel.logoutRequest()
            /*session.setIsUserLoggedIn("logout")
            val intent = Intent(activity, SignInActivity::class.java)
            navigateTo(intent, true)*/
        }
        mAlertDialog.show()
    }
    fun alertDailogSetPassword() {
        val mAlertDialog = AlertDialog.Builder(this)
        mAlertDialog.setTitle("Alert") //set alertdialog title
        mAlertDialog.setMessage(getString(R.string.you_have_set_your_password)) //set alertdialog message
        mAlertDialog.setPositiveButton("Okay") { dialog, id ->
           // session.setIsUserLoggedIn("logout")
            viewModel.logoutRequest()
           /* val intent = Intent(activity, SignInActivity::class.java)
            navigateTo(intent, true)*/
        }
        mAlertDialog.show()
    }

    override fun onSuccessLogin(changePasswordResponse: ChangePasswordResponse) {

    }

    override fun onSuccessLogout(logoutResponse: LogoutResponse) {

    }

    override fun onTokenChangeError(message: String) {

    }



    override fun onShowBaseLoader() {

    }

    override fun onHideBaseLoader() {

    }

    override fun onError(errorMessage: String) {
        toastMessage(errorMessage,this)
    }
}
