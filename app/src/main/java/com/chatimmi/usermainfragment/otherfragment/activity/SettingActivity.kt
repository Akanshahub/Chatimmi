package com.chatimmi.usermainfragment.otherfragment.activity

import com.chatimmi.socketchat.SocketConstant
import android.app.AlertDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.chatimmi.R
import com.chatimmi.app.pref.Session
import com.chatimmi.app.utils.CommonTaskPerformer
import com.chatimmi.app.utils.UIStateManager
import com.chatimmi.app.utils.showToast
import com.chatimmi.base.BaseActivitykt
import com.chatimmi.databinding.ActivitySettingBinding
import com.chatimmi.model.LogoutResponse
import com.chatimmi.repository.LogoutRepository
import com.chatimmi.retrofitnetwork.ApiCallback
import com.chatimmi.viewmodel.SettingLogoutViewModelFoctory
import com.chatimmi.viewmodel.SettingViewModel
import com.chatimmi.views.SignInActivity

@Suppress("DEPRECATION")
class SettingActivity : BaseActivitykt(), CommonTaskPerformer,ApiCallback.LogoutCallback {
    lateinit var binding: ActivitySettingBinding
    lateinit var viewModal: SettingViewModel
    lateinit var session: Session
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_setting)
        val logoutRepository = LogoutRepository(this,this)
        val factory = SettingLogoutViewModelFoctory(logoutRepository)
        viewModal = ViewModelProviders.of(this, factory).get(SettingViewModel::class.java)
        binding.settingViewModel = viewModal
        session = Session(activity)
        binding.lifecycleOwner = this
        viewModal.init(this)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val decor = window.decorView
            decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        if (session.getUserData()!!.data!!.user_details.password != null && !session.getUserData()!!.data!!.user_details.password!!.isEmpty()) {
            binding.tvPassword.text = getString(R.string.change_password)
        } else {
            binding.tvPassword.text = getString(R.string.set_password)
        }

        logoutRepository.getLogOutResponseData().observe(this, androidx.lifecycle.Observer {
            it?.let {
                when (it) {
                    is UIStateManager.Success<*> -> {
                        val getData = it.data as LogoutResponse
                        SocketConstant()!!.closeConnection()
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
        binding.backButton.setOnClickListener { onBackPressed() }
    }

    override fun <T> performAction(clazz: Class<T>) {
        Intent(this, clazz).apply {
            startActivity(this)
        }
    }

    override fun showMsg(msg: String) {
        this.showToast(msg)
    }

    override fun dismissDialog() {

    }

    override fun launchAction() {
        alertDailog()
    }

    override fun connectClick(userID: Int) {

    }

    fun alertDailog() {
        val mAlertDialog = AlertDialog.Builder(this)

        mAlertDialog.setTitle("Alert") //set alertdialog title
        mAlertDialog.setMessage("Are you sure you want to logout?") //set alertdialog message
        mAlertDialog.setPositiveButton("Yes") { dialog, id ->
            //session.setIsUserLoggedIn("logout")
            viewModal.logoutRequest()
           /* val intent = Intent(activity, SignInActivity::class.java)
            navigateTo(intent, true)*/
            Log.d("kkk", "logout: ${session.getIsUserLoggedIn()}")


        }
        mAlertDialog.setNegativeButton("No") { dialog, id ->
            dialog.dismiss()
        }
        mAlertDialog.show()
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
        Toast.makeText(activity, errorMessage, Toast.LENGTH_LONG).show()
    }
}