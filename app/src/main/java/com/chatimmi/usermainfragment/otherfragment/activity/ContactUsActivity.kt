package com.chatimmi.usermainfragment.otherfragment.activity

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.chatimmi.R
import com.chatimmi.app.utils.UIStateManager
import com.chatimmi.app.utils.showToast
import com.chatimmi.base.BaseActivitykt
import com.chatimmi.databinding.ActivityContactUsBinding
import com.chatimmi.model.ContactUsResponse
import com.chatimmi.repository.ContactUsRepository
import com.chatimmi.retrofitnetwork.ApiCallback
import com.chatimmi.viewmodel.ContactUsViewModel
import com.chatimmi.viewmodel.ContactUsViewModelFactory


class ContactUsActivity : BaseActivitykt(),ApiCallback.ContactUsCallBack {
    lateinit var binding: ActivityContactUsBinding
    lateinit var viewModel: ContactUsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_contact_us)
        val contactUsRepository = ContactUsRepository(this,this)
        val factory = ContactUsViewModelFactory(contactUsRepository)
        viewModel = ViewModelProviders.of(this, factory).get(ContactUsViewModel::class.java)
        binding.lifecycleOwner = this
        binding.contactUsViewModel = viewModel
        viewModel.getValidationData().observe(this, Observer {
            when (it) {
                is UIStateManager.Error -> {
                    showToast(it.msg)
                }
                else -> {
                }
            }

        })
        contactUsRepository.getResponseData().observe(this, Observer {
            it?.let {
                when (it) {
                    is UIStateManager.Success<*> -> {
                        val getData = it.data as ContactUsResponse
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
                    else -> {
                    }
                }
            }
        })
        binding.appBar.ivBtnBack.setOnClickListener { onBackPressed() }
    }

    fun alertDailog() {
        val mAlertDialog = AlertDialog.Builder(this)
        mAlertDialog.setTitle("Alert") //set alertdialog title
        mAlertDialog.setMessage(getString(R.string.feedback_sent)) //set alertdialog message
        mAlertDialog.setPositiveButton("Okay") { dialog, id ->
            onBackPressed()
        }
        mAlertDialog.show()
    }

    override fun onSuccessLogin(contactUsResponse: ContactUsResponse) {


    }

    override fun onShowBaseLoader() {

    }

    override fun onHideBaseLoader() {

    }

    override fun onError(errorMessage: String) {
        toastMessage(errorMessage,this)
    }

}