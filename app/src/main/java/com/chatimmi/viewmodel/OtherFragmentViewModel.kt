package com.chatimmi.viewmodel

import androidx.lifecycle.ViewModel
import com.chatimmi.app.utils.CommonTaskPerformer
import com.chatimmi.repository.LogoutRepository
import com.chatimmi.usermainfragment.otherfragment.activity.*
import com.chatimmi.usermainfragment.otherfragment.myProfile.MyProfileActivity
import java.util.*

class OtherFragmentViewModel(val logoutRepository: LogoutRepository) : ViewModel() {

    lateinit var commonTaskPerformer: CommonTaskPerformer
    fun init(commonTaskPerformer: CommonTaskPerformer) {
        this.commonTaskPerformer = commonTaskPerformer
    }

    fun llSettingOnClicked() {
        commonTaskPerformer.performAction(SettingActivity::class.java)

    }

    fun llContactUsOnClicked() {
        commonTaskPerformer.performAction(ContactUsActivity::class.java)

    }

    fun llTermAndConditionsOnClicked() {
        commonTaskPerformer.performAction(TermAndCond::class.java)

    }

    fun llPrivacyPolicyOnClicked() {
        commonTaskPerformer.performAction(PrivacyPolicy::class.java)

    }

    fun llFAQUsOnClicked() {
        commonTaskPerformer.performAction(FqaActivity::class.java)
    }

    fun llLogoutOnClicked() {
        commonTaskPerformer.launchAction()
    }

    fun rlProfileOnClicked() {
        commonTaskPerformer.performAction(MyProfileActivity::class.java)
    }

    fun logoutRequest() {
        logoutRepository.callLogoutApi(UUID.randomUUID().toString(), "hdjhfhdjh", "2", TimeZone.getDefault().displayName)
    }
}