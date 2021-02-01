package com.chatimmi.viewmodel

import androidx.lifecycle.ViewModel
import com.chatimmi.app.utils.CommonTaskPerformer
import com.chatimmi.usermainfragment.otherfragment.activity.ChangePasswordActivity
import com.chatimmi.usermainfragment.otherfragment.activity.EditProfileActivity

import com.chatimmi.views.SignInActivity


class SettingViewModel : ViewModel() {
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
        commonTaskPerformer.performAction(SignInActivity::class.java)
    }
}