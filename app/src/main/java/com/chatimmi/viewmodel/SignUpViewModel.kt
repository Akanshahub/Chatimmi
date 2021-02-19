package com.chatimmi.viewmodel

import android.graphics.Bitmap
import android.net.Uri
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chatimmi.app.utils.UIStateManager
import com.chatimmi.repository.SignUpRepository
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


class SignUpViewModel(val signupRepository: SignUpRepository) :
        ViewModel() {
    lateinit var imageUri: Uri
    lateinit var bitmap: Bitmap
    var emailAddress = ""
    var password = ""
    var fullName = ""
    var passwordConfirm = ""
    var userType = "1"
    var PASSWORD_PATTERN_STRING = "/^(?=.*\\d)(?=.*[A-Z])(?=.*[^a-zA-Z0-9])(?!.*\\s).{8,}\$/"

     var profilePicture: MultipartBody.Part? =null
    var file: File? = null

     var isSelect: Boolean = false

    private val validationObserver by lazy {
        MutableLiveData<UIStateManager>()
    }

    fun getValidationData() = validationObserver as LiveData<UIStateManager>


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

    }








    private fun validate(): Boolean {

        if (fullName.isEmpty()) {
            validationObserver.value = UIStateManager.Error("Please enter your full name")
           // Toast.makeText(this, getString(R.string.please_enter_full_name), Toast.LENGTH_SHORT).show();
            return false
        }
      /*  if (fullName.length < 2) {
            validationObserver.value = UIStateManager.Error("FullName must be atleast 2 characters")
          //  Toast.makeText(this, getString(R.string.fullname_must_6_digt), Toast.LENGTH_SHORT).show();
            return false
        }*/
        if ("" == emailAddress) {
            validationObserver.value = UIStateManager.Error("Please enter your email")



            return false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()) {
            validationObserver.value = UIStateManager.Error("Email address is not valid, please provide a valid email")
            return false
        }
        if ("" == password) {
            validationObserver.value = UIStateManager.Error("Please enter your password")
            return false
        }

/*        if (!Pattern.compile(PASSWORD_PATTERN_STRING).matcher(password).matches()) {
            validationObserver.value = UIStateManager.Error("Password should be 8 characters long and contains one uppercase character, one special character and at least one number")
            return false
        }*/
        if (passwordConfirm.isEmpty()) {
            validationObserver.value = UIStateManager.Error("Please enter your confirm password")
            //Toast.makeText(this, getString(R.string.please_enter_password_to_confirm), Toast.LENGTH_SHORT).show();
            return false
        }

        if (passwordConfirm != password) {
            validationObserver.value = UIStateManager.Error("Password and confirm password does not match")
           // Toast.makeText(this, getString(R.string.confirm_password_does_not_match), Toast.LENGTH_SHORT).show();
            return false
        }
        if (!isSelect) {
            validationObserver.value = UIStateManager.Error("Please accept terms & conditions and privacy policy")
            return false
        }
        //content://media/external/images/media/727

       // file = File()
        if (file != null) {

            val file: File = file!!

            val reqFile: RequestBody = RequestBody.create("image/*".toMediaTypeOrNull(), file)
            val body: MultipartBody.Part =  MultipartBody.Part.createFormData("profilePicture", file.name, reqFile)

            profilePicture  = body

//            = MultipartBody.Part.createFormData(
//                    "profilePicture", file.name, file.asRequestBody("image/*".toMediaTypeOrNull()))
        }
        return true
    }


/*    fun validate(): Boolean {


        val name = binding!!.etName.text.toString()
        val email = binding!!.etEmail.text.toString()
        val password = binding!!.etPassword.text.toString()
        val passwordConfirm = binding!!.etPasswordConfirm.text.toString()
        if (name.isEmpty()) {
            Toast.makeText(this, getString(R.string.please_enter_full_name), Toast.LENGTH_SHORT).show();
            return false
        }
        if (name.length < 2) {
            Toast.makeText(this, getString(R.string.fullname_must_6_digt), Toast.LENGTH_SHORT).show();
            return false
        }
        if (email.isEmpty()) {
            Toast.makeText(this, getString(R.string.please_enter_email), Toast.LENGTH_SHORT).show();
            return false
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, getString(R.string.please_enter_valid_email), Toast.LENGTH_SHORT).show();
            return false
        }
        if (password.isEmpty()) {
            Toast.makeText(this, getString(R.string.please_enter_password), Toast.LENGTH_SHORT).show();
            return false
        }
        if (password.length < 6) {
            Toast.makeText(this, getString(R.string.please_enter_password_with_minimum), Toast.LENGTH_SHORT).show();
            return false
        }
        *//*    if (!Pattern.compile(PASSWORD_PATTERN_STRING).matcher(password).matches()) {
                Toast.makeText(this, getString(R.string.please_enter_valid_password), Toast.LENGTH_SHORT).show();

                return false
            }*//*
        if (passwordConfirm.isEmpty()) {
            Toast.makeText(this, getString(R.string.please_enter_password_to_confirm), Toast.LENGTH_SHORT).show();
            return false
        }
        if (passwordConfirm.length < 6) {

            Toast.makeText(this, getString(R.string.comfrim_passwor_not_less_than_6_characters), Toast.LENGTH_SHORT).show();
            return false
        }
        if (passwordConfirm != password) {

            Toast.makeText(this, getString(R.string.confirm_password_does_not_match), Toast.LENGTH_SHORT).show();
            return false
        }
        if (!binding!!.cbTerm.isChecked) {

            showToast(getString(R.string.please_accept_termconditions_and_privacy_policy))
            return false
        }

        return true
    }*/


}