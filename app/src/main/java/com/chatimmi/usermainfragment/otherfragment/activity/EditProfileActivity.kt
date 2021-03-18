package com.chatimmi.usermainfragment.otherfragment.activity

import android.Manifest
import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.webkit.MimeTypeMap
import androidx.annotation.Nullable
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.chatimmi.R
import com.chatimmi.app.pref.Session
import com.chatimmi.app.utils.UIStateManager
import com.chatimmi.app.utils.showToast
import com.chatimmi.base.BaseActivitykt
import com.chatimmi.databinding.ActivityEditProfileBinding

import com.chatimmi.helper.ImagePicker
import com.chatimmi.model.UserDetails
import com.chatimmi.model.UserDetialResponse
import com.chatimmi.repository.EditProfileRepository
import com.chatimmi.viewmodel.EditProfileViewModel
import com.chatimmi.viewmodel.EditProfileViewModelFactory
import com.yalantis.ucrop.UCrop
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

@Suppress("DEPRECATION")
class EditProfileActivity : BaseActivitykt() {
    lateinit var viewModel: EditProfileViewModel
    lateinit var binding: ActivityEditProfileBinding
    private var avatarUri: Uri? = null
    private var mimeType: String? = null
    lateinit var session: Session
    private var temPhoto: Uri? = null
    lateinit var userDetail: UserDetails

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_profile)

        val editProfileRepository = EditProfileRepository(this)
        val factory = EditProfileViewModelFactory(editProfileRepository)

        viewModel = ViewModelProviders.of(this, factory).get(EditProfileViewModel::class.java)
        session = Session(this)
        binding.lifecycleOwner = this
        binding.editProfileViewModel = viewModel
        userDetail = session.getUserData()!!.data!!.user_details
        viewModel.getValidationData().observe(this, Observer {
            when (it) {
                is UIStateManager.Error -> {
                if(it.msg=="full_name"){
                    showToast(getString(R.string.please_enter_full_name))
                }else if(it.msg=="characters"){
                    showToast(getString(R.string.fullname_must_2_digt))
                }else if(it.msg=="email"){
                    showToast(getString(R.string.please_enter_email))
                }else if(it.msg=="valid_email"){
                    showToast(getString(R.string.please_enter_valid_email))
                }
                //showToast(it.msg)
                }
                else -> {
                }
            }

        })

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val decor = window.decorView
            decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        editProfileRepository.editProfileResponseData().observe(this, Observer {
            it?.let {
                when (it) {
                    is UIStateManager.Success<*> -> {
                        val getData = it.data as UserDetialResponse
                        session.setUserData(getData)
                        toastMessage(getData.message,this)
                      /*  val intent = Intent(this@EditProfileActivity, ChatimmiActivity::class.java)
                        navigateTo(intent, true)*/

                    }
                    is UIStateManager.Error -> {
                        showToast(it.msg)
                    }
                    is UIStateManager.Loading ->{
                        if(it.shouldShowLoading){
                            showLoader()
                        }else{
                            hideLoader()
                        }

                    }
                    else -> {
                    }
                }
            }
        })
        initialSetup()
    }

    private fun initialSetup() {
        Glide.with(activity).load(userDetail.avatar).into(binding.ivImages)
        viewModel.userName = userDetail.full_name!!
        viewModel.emailAddress = userDetail.email!!
        binding.etName.setText(viewModel.userName)
        binding.etEmail.setText(viewModel.emailAddress)

        binding.ivImages.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(this@EditProfileActivity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE), 99)
                } else {
                    ImagePicker.pickImage(this@EditProfileActivity)
                }
            } else {
                ImagePicker.pickImage(this@EditProfileActivity)
            }
        }
        binding.backButton.setOnClickListener {

            setResult(RESULT_OK)
            onBackPressed()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, @Nullable data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == UCrop.REQUEST_CROP && resultCode == RESULT_OK) {
            val uri = UCrop.getOutput(data!!)

            data.let {
                Log.d("fbasjfbajsfa", "onActivityResult: ${uri}")
            }

            temPhoto = uri
            viewModel.imageUri = uri!!

            var bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)

            viewModel.file = getImageFile(uri)
            try {
                showImage(uri)

                bitmap = getBitmapFromUri(uri)
                avatarUri = uri
                mimeType = getMimeType(avatarUri)

            } catch (e: IOException) {
                e.printStackTrace()
                Log.e("erroris", e.printStackTrace().toString())
            }
        } else if (requestCode == ImagePicker.PICK_IMAGE_REQUEST_CODE && resultCode == RESULT_OK) {
            Log.d("fbasjfbajsfa", "PICK_IMAGE_REQUEST_CODE: ")

            val uri: Uri = ImagePicker.getImageURIFromResult(this@EditProfileActivity, requestCode, resultCode, data)
            openCropActivity(uri)
        }
        if (resultCode == UCrop.RESULT_ERROR) {
            val result = UCrop.getError(data!!)
            val error = result!!.message
        }
    }

    private fun getImageFile(uri: Uri): File {
        val bitmap: Bitmap =
                MediaStore.Images.Media.getBitmap(contentResolver, uri)

        val f = File(cacheDir, "filename");
        f.createNewFile()

        val bos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50 /*ignored for PNG*/, bos)
        val bitmapdata = bos.toByteArray()
        val fos = FileOutputStream(f)
        fos.write(bitmapdata)
        fos.flush()
        fos.close()
        Log.d("fffdfdffkkf", "MultipartBody: ${f.name}")
        return f
    }

    private fun showImage(imageUri: Uri) {
        binding.ivImages.setImageURI(imageUri)
    }

    @Throws(IOException::class)
    private fun getBitmapFromUri(uri: Uri): Bitmap {
        val parcelFileDescriptor = contentResolver.openFileDescriptor(uri, "r")
        val fileDescriptor = parcelFileDescriptor!!.fileDescriptor
        val image = BitmapFactory.decodeFileDescriptor(fileDescriptor)
        parcelFileDescriptor.close()
        return image
    }


    private fun openCropActivity(sourceUri: Uri) {
        val time: Long = System.currentTimeMillis()
        val str = "" + time
        val destinationPath = str + "temp.jpg"
        val options: UCrop.Options = UCrop.Options()
        options.setHideBottomControls(true)
        UCrop.of(sourceUri, Uri.fromFile(File(cacheDir, destinationPath))).withAspectRatio(4F, 3F).withOptions(options).start(this)
    }


    private fun getMimeType(uri: Uri?): String? {
        var mimeType: String? = null
        mimeType = if (uri!!.scheme == ContentResolver.SCHEME_CONTENT) {
            val cr = contentResolver
            cr.getType(uri)
        } else {
            val fileExtension = MimeTypeMap.getFileExtensionFromUrl(uri.toString())
            MimeTypeMap.getSingleton().getMimeTypeFromExtension(
                    fileExtension.toLowerCase())
        }
        return mimeType
    }

}