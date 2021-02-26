package com.chatimmi.viewmodel

import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.chatimmi.app.utils.CommonTaskPerformer
import com.chatimmi.repository.LogoutRepository
import com.chatimmi.usermainfragment.activity.notification.NotificationActivity
import com.chatimmi.usermainfragment.otherfragment.activity.*
import com.chatimmi.usermainfragment.otherfragment.myProfile.MyProfileActivity

class OtherFragmentViewModel(val logoutRepository: LogoutRepository) : ViewModel() {
    var termAndCond = ""
    var privacyPolicy = ""
    lateinit var commonTaskPerformer: CommonTaskPerformer
    fun init(commonTaskPerformer: CommonTaskPerformer) {
        logoutRepository.callContentApi()
        this.commonTaskPerformer = commonTaskPerformer

    }

    fun llSettingOnClicked() {
        commonTaskPerformer.performAction(SettingActivity::class.java, null, false)

    }

    fun llContactUsOnClicked() {
        commonTaskPerformer.performAction(ContactUsActivity::class.java, null, false)

    }

    fun llTermAndConditionsOnClicked() {
        val bundle = Bundle()
        bundle.putString("termAndCond",termAndCond)
        commonTaskPerformer.performAction(TermAndCond::class.java,bundle, true)

    }

    fun llPrivacyPolicyOnClicked() {
        val bundle = Bundle()
        bundle.putString("privacyPolicy",privacyPolicy)
        commonTaskPerformer.performAction(PrivacyPolicy::class.java, bundle,true)

    }

    fun llFAQUsOnClicked() {
        commonTaskPerformer.performAction(FqaActivity::class.java, null, false)
    }

    fun llLogoutOnClicked() {
        commonTaskPerformer.launchAction()
    }

    fun rlProfileOnClicked() {
        commonTaskPerformer.performAction(MyProfileActivity::class.java, null, false)
    }

    fun llNotificationOnClicked() {
        commonTaskPerformer.performAction(NotificationActivity::class.java, null, false)
    }

    fun logoutRequest() {
        logoutRepository.callLogoutApi()
    }
}