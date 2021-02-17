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
import android.util.Log
import android.view.View
import android.webkit.MimeTypeMap
import androidx.annotation.Nullable
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.chatimmi.Chatimmi
import com.chatimmi.R
import com.chatimmi.app.utils.UIStateManager
import com.chatimmi.app.utils.showToast
import com.chatimmi.base.BaseActivitykt
import com.chatimmi.databinding.ActivityEditProfileBinding

import com.chatimmi.helper.ImagePicker
import com.chatimmi.viewmodel.EditProfileViewModel
import com.yalantis.ucrop.UCrop
import java.io.File
import java.io.IOException

@Suppress("DEPRECATION")
class EditProfileActivity : BaseActivitykt() {
    lateinit var viewModel: EditProfileViewModel
    lateinit var binding: ActivityEditProfileBinding
    private var bitmap: Bitmap? = null
    private var avatarUri: Uri? = null
    private var mimeType: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(EditProfileViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_profile)

        mSocket = Chatimmi().getSocket()
        binding.lifecycleOwner = this
        binding.editProfileViewModel = viewModel

        viewModel.getValidationData().observe(this, Observer {
            when (it) {
                is UIStateManager.Error ->{
                    showToast(it.msg)
                }
                else -> {
                }
            }

        })

        viewModel.getUpdateData().observe(this, Observer {
            it?.let {
                when (it) {
                    is UIStateManager.Success<*> -> {
                        showToast("Under Development")
                    }
                    is UIStateManager.Error ->{
                        showToast(it.msg)
                    }
                    else -> {
                    }
                }
            }
        })
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val decor = window.decorView
            decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        initialSetup()
    }

    private fun initialSetup() {



        binding.ivCamera.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {

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
        })
        binding.backButton.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, @Nullable data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == UCrop.REQUEST_CROP && resultCode == RESULT_OK) {
            val uri = UCrop.getOutput(data!!)
            try {
                showImage(uri!!)
                bitmap = getBitmapFromUri(uri)
                avatarUri = uri
                mimeType = getMimeType(avatarUri)

            } catch (e: IOException) {
                e.printStackTrace()
                Log.e("erroris", e.printStackTrace().toString())
            }
        }  else if (requestCode == ImagePicker.PICK_IMAGE_REQUEST_CODE && resultCode == RESULT_OK) {
            val uri: Uri = ImagePicker.getImageURIFromResult(this@EditProfileActivity, requestCode, resultCode, data)
            openCropActivity(uri)
        }
        if (resultCode== UCrop.RESULT_ERROR)  {
            val result = UCrop.getError(data!!)
            val error = result!!.message
        }
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
        val time:Long = System.currentTimeMillis()
        val str = "" + time
        val destinationPath = str + "temp.jpg"
        val options: UCrop.Options = UCrop.Options()
        options.setHideBottomControls(true)
        UCrop.of(sourceUri, Uri.fromFile(File( getCacheDir(), destinationPath))).withAspectRatio(4F, 3F).withOptions(options).start(this)
    }


    private fun getMimeType(uri: Uri?): String? {
        var mimeType: String? = null/*  if (validate()) {
            updateResponseObserver.value=UIStateManager.Success("updated")

        }else{
          //  updateResponseObserver.value=UIStateManager.Error("Failed")
        }*/
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