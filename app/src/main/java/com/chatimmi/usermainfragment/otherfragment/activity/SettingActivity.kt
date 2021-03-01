package com.chatimmi.usermainfragment.otherfragment.activity

import android.app.AlertDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.chatimmi.R
import com.chatimmi.app.pref.Session
import com.chatimmi.app.utils.CommonTaskPerformer
import com.chatimmi.app.utils.UIStateManager
import com.chatimmi.app.utils.showToast
import com.chatimmi.base.BaseActivitykt
import com.chatimmi.databinding.ActivitySettingBinding
import com.chatimmi.model.LogoutResponse
import com.chatimmi.model.UserDetails
import com.chatimmi.model.UserDetialResponse
import com.chatimmi.repository.LogoutRepository
import com.chatimmi.retrofitnetwork.ApiCallback
import com.chatimmi.socketchat.SocketCont
import com.chatimmi.viewmodel.SettingLogoutViewModelFoctory
import com.chatimmi.viewmodel.SettingViewModel

@Suppress("DEPRECATION")
class SettingActivity : BaseActivitykt(), CommonTaskPerformer, ApiCallback.LogoutCallback, ApiCallback.NotificationSwitchCallBack {
    lateinit var binding: ActivitySettingBinding
    lateinit var viewModal: SettingViewModel
    lateinit var session: Session
    lateinit var userDetail: UserDetails
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_setting)
        val logoutRepository = LogoutRepository(this, this, this)
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
        userDetail = session.getUserData()!!.data!!.user_details
        if (session.getUserData()!!.data!!.user_details.password != null && !session.getUserData()!!.data!!.user_details.password!!.isEmpty()) {
            binding.tvPassword.text = getString(R.string.change_password)
        } else {
            binding.tvPassword.text = getString(R.string.set_password)
        }

        if (session.getNotificationStatus()!!.isEmpty()) {
            session.setNotificationStatus(session.getUserData()!!.data!!.user_details.push_alert_status!!)
        }
        if (session.getNotificationStatus()!!.equals("0")) {
            binding.ivSettings.setImageResource(R.drawable.off_toggle_off)
        } else if (session.getNotificationStatus()!!.equals("1")) {
            binding.ivSettings.setImageResource(R.drawable.on_toogle_icon)

        }
        logoutRepository.getLogOutResponseData().observe(this, androidx.lifecycle.Observer {
            it?.let {
                when (it) {
                    is UIStateManager.Success<*> -> {
                        val getData = it.data as LogoutResponse
                        SocketCont().closeConnection(SocketCont.getSocketInstance(), this)
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
        binding.ivSettings.setOnClickListener {
            if ("1" == session.getNotificationStatus()) {
                binding.ivSettings.setImageResource(R.drawable.off_toggle_off)
                logoutRepository.callNotificationSwitchApi("0")
                session.setNotificationStatus("0")

            } else {
                binding.ivSettings.setImageResource(R.drawable.on_toogle_icon)
                logoutRepository.callNotificationSwitchApi("1")
                session.setNotificationStatus("1")
            }

        }
        logoutRepository.ResponseObserver().observe(this, Observer {
            it?.let {
                when (it) {
                    is UIStateManager.Success<*> -> {
                        val getData = it.data as UserDetialResponse

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


    override fun <T> performAction(clazz: Class<T>, bundle: Bundle?, isRequried: Boolean) {

        Intent(this, clazz).apply {
            if (isRequried) {
                this.putExtras(bundle!!)
            }
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

    override fun onSuccessLogin(joinGroupResponse: UserDetialResponse) {

    }

    override fun onShowBaseLoader() {

    }

    override fun onHideBaseLoader() {


    }

    override fun onError(errorMessage: String) {
        Toast.makeText(activity, errorMessage, Toast.LENGTH_LONG).show()
    }
}