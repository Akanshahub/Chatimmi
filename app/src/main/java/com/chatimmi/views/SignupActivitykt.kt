package com.chatimmi.views


import android.Manifest
import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Typeface
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.Spannable
import android.text.SpannableString
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.StyleSpan
import android.util.Log
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.CompoundButton
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.chatimmi.Chatimmi
import com.chatimmi.R
import com.chatimmi.app.pref.Session
import com.chatimmi.app.utils.UIStateManager
import com.chatimmi.app.utils.showToast
import com.chatimmi.base.BaseActivitykt
import com.chatimmi.databinding.ActivitySignupBinding
import com.chatimmi.fragmentchatimmi.ChatimmiActivity
import com.chatimmi.helper.ImagePicker
import com.chatimmi.helper.ImagePicker.PICK_IMAGE_REQUEST_CODE
import com.chatimmi.model.UserDetialResponse
import com.chatimmi.repository.SignUpRepository
import com.chatimmi.retrofitnetwork.ApiCallback
import com.chatimmi.usermainfragment.otherfragment.activity.PrivacyPolicy
import com.chatimmi.usermainfragment.otherfragment.activity.TermAndCond
import com.chatimmi.viewmodel.SignUpViewModalFactory
import com.chatimmi.viewmodel.SignUpViewModel
import com.yalantis.ucrop.UCrop
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*


class SignupActivitykt : BaseActivitykt(),ApiCallback.SignUpCallback {
var signUpViewModel: SignUpViewModel? = null
    private var binding: ActivitySignupBinding? = null
    private var bitmap: Bitmap? = null
    private var avatarUri: Uri? = null
    private val file: File? = null
    private var imageUri: Uri? = null
    private var mimeType: String? = null
    private var temPhoto: Uri? = null
    lateinit var session: Session
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val signUpRepository = SignUpRepository(this,this)
        val factory = SignUpViewModalFactory(signUpRepository)
        signUpViewModel = ViewModelProviders.of(this, factory).get(SignUpViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup)
        binding?.lifecycleOwner = this
        binding!!.signUpViewModel = signUpViewModel
        binding?.invalidateAll()
        session = Session(this)
        /*viewModel.getValidationData().observe(this, Observer {
    it?.let {
        val msg = it as UIStateManager.Error
        showToast("Under Development")
    }

})*/

        binding?.cbTerm!!.setOnCheckedChangeListener { buttonView: CompoundButton?, isChecked: Boolean -> signUpViewModel!!.isSelect = isChecked }
        signUpViewModel!!.getValidationData().observe(this, androidx.lifecycle.Observer {
            it?.let {
                val msg = it as UIStateManager.Error
                showToast(it.msg)
            }
        })

        signUpRepository.getSignUpResponseData().observe(this, androidx.lifecycle.Observer {
            it?.let {
                when (it) {
                    is UIStateManager.Success<*> -> {

                        val getData = it.data as UserDetialResponse
                        session.setUserData(getData)
                        Chatimmi.authorization = session.getAuthToken()
                        Log.d("fbasfbjasfa", "onCreate: $getData")
                      /*  showToast(getData.message.toString())*/
                        // showToast("Under Development")
                        Log.d("fbasfbjasfa", "onCreate: $getData")
                        val intent = Intent(this@SignupActivitykt, ChatimmiActivity::class.java)
                        navigateTo(intent, true)

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
        prepareSignInLink()
        initialSetup()
    }

    private fun initialSetup() {

        binding!!.tvPrivacyPolicy.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {

                val intent = Intent(this@SignupActivitykt, PrivacyPolicy::class.java)
                navigateTo(intent, false)


            }
        })
        binding!!.tvTerm.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {

                val intent = Intent(this@SignupActivitykt, TermAndCond::class.java)
                navigateTo(intent, false)
            }

        })
        binding!!.appBar.ivBtnBack.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {

                onBackPressed()

            }

        })
        binding!!.btnSignup.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                /* if (validate()) {
                    val intent = Intent(this@SignupActivitykt, ChatimmiActivity::class.java)
                    //  intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                     startActivity(intent)
                   // Toast.makeText(this@SignupActivitykt, "Under Development", Toast.LENGTH_SHORT).show();
                }*/
            }
        })
        binding!!.ivImages.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(this@SignupActivitykt, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE), 99)
                    } else {
                        ImagePicker.pickImage(this@SignupActivitykt)
                    }
                } else {
                    ImagePicker.pickImage(this@SignupActivitykt)
                }

            }
        })

    }

    fun prepareSignInLink() {
        val msg = getString(R.string.already_have_a_account_sign_in)
        val s = getString(R.string.sign_in)
        val spannable = SpannableString(msg)
        spannable.setSpan(object : ClickableSpan() {


            override fun updateDrawState(@NonNull ds: TextPaint) {
                ds.color = ContextCompat.getColor(this@SignupActivitykt, R.color.app_color)
                ds.isUnderlineText = false
            }

            override fun onClick(widget: View) {
                val intent = Intent(this@SignupActivitykt, SignInActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)
            }


        }, msg.indexOf(s), msg.indexOf(s) + s.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannable.setSpan(
                StyleSpan(Typeface.BOLD),
                msg.indexOf(s),
                msg.indexOf(s) + s.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        binding!!.tvLogin.movementMethod = LinkMovementMethod()
        binding!!.tvLogin.highlightColor = Color.TRANSPARENT
        binding!!.tvLogin.setText(spannable)
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, @Nullable data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == UCrop.REQUEST_CROP && resultCode == RESULT_OK) {
            val uri = UCrop.getOutput(data!!)

            data?.let {
                Log.d("fbasjfbajsfa", "onActivityResult: ${uri}")
            }

            temPhoto = uri
            signUpViewModel!!.imageUri= uri!!

            var bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
            signUpViewModel!!.file= getImageFile(uri)
            try {
                showImage(uri!!)

                bitmap = getBitmapFromUri(uri)
                avatarUri = uri
                mimeType = getMimeType(avatarUri)

            } catch (e: IOException) {
                e.printStackTrace()
                Log.e("erroris", e.printStackTrace().toString())
            }
        } else if (requestCode == PICK_IMAGE_REQUEST_CODE && resultCode == RESULT_OK) {
            Log.d("fbasjfbajsfa", "PICK_IMAGE_REQUEST_CODE: ")

            val uri: Uri = ImagePicker.getImageURIFromResult(this@SignupActivitykt, requestCode, resultCode, data)
            openCropActivity(uri)
        }
        if (resultCode == UCrop.RESULT_ERROR) {
            val result = UCrop.getError(data!!)
            val error = result!!.message
        }
    }

    private fun getImageFile(uri: Uri): File{
        val bitmap: Bitmap =
                MediaStore.Images.Media.getBitmap(contentResolver, uri)

        val f = File(cacheDir, "filename");
        f.createNewFile()

        val bos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos)
        val bitmapdata = bos.toByteArray()
        val fos = FileOutputStream(f)
        fos.write(bitmapdata)
        fos.flush()
        fos.close()
        Log.d("fffdfdffkkf", "MultipartBody: ${f.name}")
        return f
    }

    private fun showImage(imageUri: Uri) {
        binding!!.ivImages.setImageURI(imageUri)
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
        UCrop.of(sourceUri, Uri.fromFile(File(getCacheDir(), destinationPath))).withAspectRatio(4F, 3F).withOptions(options).start(this)
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

    override fun onBackPressed() {
        val intent = Intent(this@SignupActivitykt, SignInActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
        super.onBackPressed()
    }

    fun bitmapToFile(bitmap: Bitmap, fileNameToSave: String): File? { // File name like "image.png"
        //create a file to write bitmap data
        var file: File? = null
        return try {
            file = File(Environment.getExternalStorageDirectory().toString() + File.separator + fileNameToSave)
            file!!.createNewFile()

            //Convert bitmap to byte array
            val bos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos) // YOU can also save it in JPEG
            val bitmapdata: ByteArray = bos.toByteArray()

            //write the bytes in file
            val fos = FileOutputStream(file)
            fos.write(bitmapdata)
            fos.flush()
            fos.close()
            file
        } catch (e: Exception) {
            e.printStackTrace()
            file // it will return null
        }
    }

    override fun onSuccessRegistration(userDetialResponse: UserDetialResponse) {

    }

    override fun onShowBaseLoader() {

    }

    override fun onHideBaseLoader() {

    }

    override fun onError(errorMessage: String) {
    toastMessage(errorMessage,this)

    }
}

