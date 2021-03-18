package com.chatimmi.viewmodel

import android.net.Uri
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chatimmi.app.utils.UIStateManager
import com.chatimmi.repository.EditProfileRepository
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class EditProfileViewModel(private val editProfileRepository: EditProfileRepository):ViewModel() {
    var emailAddress = ""
    var userName = ""
    lateinit var imageUri: Uri
    var profilePicture: MultipartBody.Part? =null
    var file: File? = null

    private val validationObserver by lazy {
        MutableLiveData<UIStateManager>()

    }
    fun getValidationData() = validationObserver as LiveData<UIStateManager>



    fun updateOnClicked() {
        if (validate()) {
            val name = RequestBody.create(
                    "text/plain".toMediaTypeOrNull(),
                    userName
            )


            editProfileRepository.calleditProfileApi(name, profilePicture)
        }

    }

    private fun validate(): Boolean {

        if ("" == userName) {
            validationObserver.value = UIStateManager.Error("full_name")

            return false
        }
        if (userName.trim().length < 2) {
            validationObserver.value = UIStateManager.Error("characters")

            return false
        }
        if ("" == emailAddress) {
            validationObserver.value = UIStateManager.Error("email")
            return false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()) {
            validationObserver.value = UIStateManager.Error("valid_email")
            //validationObserver.value = UIStateManager.Error("Email address is not valid, please provide a valid email")
            return false
        }

        if (file != null) {

            //val reqFile: RequestBody = RequestBody.create("image/*".toMediaTypeOrNull(), file!!)
            val rbody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file!!)

            val body: MultipartBody.Part =  MultipartBody.Part.createFormData("profilePicture", file!!.name, rbody)

            profilePicture  = body

//            = MultipartBody.Part.createFormData(
//                    "profilePicture", file.name, file.asRequestBody("image/*".toMediaTypeOrNull()))
        }
        return true
    }
}