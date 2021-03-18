package com.chatimmi.viewmodel

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chatimmi.app.utils.CommonTaskPerformer
import com.chatimmi.app.utils.UIStateManager
import com.chatimmi.repository.SignUpRepository
import com.chatimmi.usermainfragment.otherfragment.activity.PrivacyPolicy
import com.chatimmi.usermainfragment.otherfragment.activity.TermAndCond
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.util.regex.Pattern


class SignUpViewModel(val signupRepository: SignUpRepository) :
        ViewModel() {
    lateinit var imageUri: Uri
    lateinit var bitmap: Bitmap
    var emailAddress = ""
    var password = ""
    var fullName = ""
    var passwordConfirm = ""
    var userType = "1"
    var termAndCond = ""
    var privacyPolicy = ""
    lateinit var commonTaskPerformer: CommonTaskPerformer
    var PASSWORD_PATTERN_STRING = "^(?=.{8,})(?=.*[A-Z])(?=.*[@#\$%^&+*!=]).*\$"
    fun init(commonTaskPerformer: CommonTaskPerformer) {
        signupRepository.callContentApi()
        this.commonTaskPerformer = commonTaskPerformer
    }

    var profilePicture: MultipartBody.Part? = null
    var file: File? = null

    var isSelect: Boolean = false

    private val validationObserver by lazy {
        MutableLiveData<UIStateManager>()
    }

    fun getValidationData() = validationObserver as LiveData<UIStateManager>
    fun llPrivacyPolicyOnClicked() {
        val bundle = Bundle()
        bundle.putString("privacyPolicy", privacyPolicy)
        commonTaskPerformer.performAction(PrivacyPolicy::class.java, bundle, true)
    }

    fun llTermAndConditionsOnClicked() {
        val bundle = Bundle()
        bundle.putString("termAndCond", termAndCond)
        commonTaskPerformer.performAction(TermAndCond::class.java, bundle, true)
    }

    fun updateOnClicked() {

        if (validate()) {
            val name = RequestBody.create(
                    "text/plain".toMediaTypeOrNull(),
                    fullName
            )

            val emaill = RequestBody.create(
                    "text/plain".toMediaTypeOrNull(),
                    emailAddress
            )

            val passwordd = RequestBody.create(
                    "text/plain".toMediaTypeOrNull(),
                    password
            )
            val cpass = RequestBody.create(
                    "text/plain".toMediaTypeOrNull(),
                    passwordConfirm
            )
            val usertype = RequestBody.create(
                    "text/plain".toMediaTypeOrNull(),
                    userType
            )

            signupRepository.SignUpCallback(name, emaill, passwordd, usertype, cpass, profilePicture)
        }
        signupRepository.callContentApi()
    }


    private fun validate(): Boolean {

        if (fullName.isEmpty()) {
            validationObserver.value = UIStateManager.Error("full_name")
            return false
        }
        if (fullName.trim().length < 2) {
            validationObserver.value = UIStateManager.Error("name")
            return false
        }
        if ("" == emailAddress) {
            validationObserver.value = UIStateManager.Error("email")
            return false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()) {
            validationObserver.value = UIStateManager.Error("valid_email")
            return false
        }
        if ("" == password) {
            validationObserver.value = UIStateManager.Error("password")
            return false
        }
        if (!Pattern.compile(PASSWORD_PATTERN_STRING).matcher(password).matches()) {
            validationObserver.value = UIStateManager.Error("number")
            //validationObserver.value = UIStateManager.Error("Password should be 8 characters long and contains one uppercase character, one special character and at least one number")
            return false
        }
        if (passwordConfirm.isEmpty()) {
            validationObserver.value = UIStateManager.Error("confirm_password")
            return false
        }
        if (passwordConfirm != password) {
            validationObserver.value = UIStateManager.Error("match")
            return false
        }
        if (!isSelect) {
            validationObserver.value = UIStateManager.Error("policy")
            return false
        }
        if (file != null) {
            val file: File = file!!
            val reqFile: RequestBody = RequestBody.create("image/*".toMediaTypeOrNull(), file)
            val body: MultipartBody.Part = MultipartBody.Part.createFormData("profilePicture", file.path, reqFile)
            profilePicture = body
        }
        return true
    }

}