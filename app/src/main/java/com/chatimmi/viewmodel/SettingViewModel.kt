package com.chatimmi.viewmodel

import androidx.lifecycle.ViewModel
import com.chatimmi.app.utils.CommonTaskPerformer
import com.chatimmi.repository.LogoutRepository
import com.chatimmi.usermainfragment.otherfragment.activity.ChangePasswordActivity
import com.chatimmi.usermainfragment.otherfragment.activity.EditProfileActivity


class SettingViewModel(val logoutRepository: LogoutRepository) : ViewModel() {
    lateinit var commonTaskPerformer: CommonTaskPerformer
    fun init(commonTaskPerformer: CommonTaskPerformer) {
        this.commonTaskPerformer = commonTaskPerformer;
    }

    fun llEditProfileOnClicked() {
        commonTaskPerformer.performAction(EditProfileActivity::class.java)
    }

    fun llChangePasswordClicked() {
        commonTaskPerformer.performAction(ChangePasswordActivity::class.java)
    }
    fun logoutOnClicked() {
        commonTaskPerformer.launchAction()
       // commonTaskPerformer.performAction(SignInActivity::class.java)
    }

    fun logoutRequest(){
        logoutRepository.callLogoutApi()
    }
}