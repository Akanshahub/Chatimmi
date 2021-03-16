package com.chatimmi.views

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.chatimmi.Chatimmi

import com.chatimmi.app.pref.Session
import com.chatimmi.app.utils.UIStateManager
import com.chatimmi.app.utils.showToast
import com.chatimmi.base.BaseActivitykt
import com.chatimmi.databinding.ActivitySignInBinding

import com.chatimmi.fragmentchatimmi.ChatimmiActivity
import com.chatimmi.model.UserDetialResponse
import com.chatimmi.repository.CheckSocialSignUpRepository
import com.chatimmi.repository.SignInRepository
import com.chatimmi.repository.socialSignUpRepository
import com.chatimmi.retrofitnetwork.ApiCallback
import com.chatimmi.viewmodel.SignInViewModalFactory
import com.chatimmi.viewmodel.SignInViewModel
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import io.socket.client.Socket
import org.json.JSONException
import org.json.JSONObject
import java.util.*
import com.chatimmi.R


@Suppress("DEPRECATION")
class SignInActivity : BaseActivitykt(), ApiCallback.CheckSocialSignupCallback, ApiCallback.SocialSignupCallback,ApiCallback.LoginCallback {
    lateinit var viewModel: SignInViewModel
    lateinit var mGoogleSignInClient: GoogleSignInClient
    private val REQ_CODE_GOOGLE = 9001
    private var binding: ActivitySignInBinding? = null
    lateinit var gso: GoogleSignInOptions
    private lateinit var callbackManager: CallbackManager
    private var EMAIL = "email"
    private var socialName = ""
    private var socialEmail = ""
    private var socialId = ""
    private var personPhoto = ""
    private var socialType = ""
    lateinit var session: Session
    var mSocket: Socket? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // socket.emit('join', {email: $("#chatUserEmail").val()});



        val signInRepository = SignInRepository(this@SignInActivity, this@SignInActivity)
        val factory = SignInViewModalFactory(signInRepository)
        viewModel = ViewModelProviders.of(this, factory).get(SignInViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in)
        binding?.lifecycleOwner = this
        binding!!.signInViewModel = viewModel
        binding?.invalidateAll()




        val w = window
        w.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        gso = if (packageName.equals("com.chatimmi")) {
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build()
        } else {
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build()
        }
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        callbackManager = CallbackManager.Factory.create()

        session = Session(this)

        binding!!.tvCreate.setOnClickListener {

            val intent = Intent(this@SignInActivity, SignupActivitykt::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        }

        binding!!.btnGoogleLogin.setOnClickListener {
            gmailSignIn()
        }
        binding!!.llFacebookLogin.setOnClickListener {
            facebookSignIn()
        }
        binding!!.buttonTwitter.setOnClickListener {
            showToast("Under development")
        }
        binding!!.tvForgotPassword.setOnClickListener {
            val intent = Intent(this@SignInActivity, ForgetPasswordActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        }

        signInRepository.getLoginResponseData().observe(this, Observer {
            it?.let {
                when (it) {
                    is UIStateManager.Success<*> -> {

                        val getData = it.data as UserDetialResponse
                        session.setUserData(getData)

                        Chatimmi.authorization = session.getAuthToken()

                       // mSocket!!.emit("join",.email)
                        val jsonObject = JSONObject()
                        try {
                            jsonObject.put("userID",  getData.data!!.user_details.userID)
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                        mSocket = Chatimmi.getSocket()
                        mSocket!!.emit("join", jsonObject)

                        Log.d("fbasfbjasfa", "onCreate: $getData")
                        session.setIsUserLoggedIn("isLogin")
                        val intent = Intent(this@SignInActivity, ChatimmiActivity::class.java)
                        navigateTo(intent, true)
                    }
                    is UIStateManager.Error -> {
                        showToast(it.msg)
                    }
                    is UIStateManager.Loading -> {

                        if (it.shouldShowLoading) {
                            this.showLoader()
                        } else {
                            this.hideLoader()
                        }
                    }
                    else -> {
                    }
                }
            }
        })





        viewModel.getValidationData().observe(this, Observer {
            it?.let {
                val msg = it as UIStateManager.Error
                showToast(it.msg)
            }

        })
    }
    fun gmailSignIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, REQ_CODE_GOOGLE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_CODE_GOOGLE) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                handleSignInResult(account!!)
                Log.d("Result", account.toString())
            } catch (e: Exception) {
                e.message?.let {
                    Log.d("Results", it)
                }
            }

        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data)
        }
    }

    fun facebookSignIn() {
        LoginManager.getInstance().logInWithReadPermissions(this, listOf(EMAIL))
        LoginManager.getInstance().registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult?) {
                val request = GraphRequest.newMeRequest(result!!.accessToken, object : GraphRequest.GraphJSONObjectCallback {
                    override fun onCompleted(obj: JSONObject?, response: GraphResponse?) {
                        socialName = obj!!.getString("name")
                        if (obj.has("email")) {
                            socialEmail = obj.getString("email")
                        } /*else {
                            socialEmail = ""
                        }*/

                        socialId = obj.getString("id")

                        /*  if (socialEmail != null && socialEmai)
                              socialEmail = socialEmail!!
                          else
                              socialEmail = socialId + "@facebook.com"*/
                        if (socialEmail.isEmpty()) {
                            socialEmail = socialId + "@facebook.com"
                        }
                        Log.d("socialssss", socialId + "@facebook.com")
                        socialType = "2"
                        val personPhoto1 = obj.getJSONObject("picture")
                        val data = personPhoto1.getJSONObject("data")
                        personPhoto = data.getString("url")

                        CheckSocialSignUpRepository(this@SignInActivity, this@SignInActivity)
                                .callCheckSocialSignUpApi(socialId, socialType, "1")


                        LoginManager.getInstance().logOut()
                    }
                })

                val parameters = Bundle()
                parameters.putString("fields", "name,email,id,picture")
                request.parameters = parameters
                request.executeAsync()
            }

            override fun onCancel() {

                Log.e("onCancel: ", "onCancel")
            }

            override fun onError(error: FacebookException?) {

                // App code
                Log.e("onError: ", error.toString())
            }
        })
    }

    fun handleSignInResult(account: GoogleSignInAccount) {
        if (account.displayName != null)
            socialName = account.displayName!!
        else
            socialName = ""

        if (account.id != null)
            socialId = account.id!!
        else
            socialId = ""

        if (account.email != null)
            socialEmail = account.email!!
        else
            socialEmail = socialId + "@facebook.com"



        socialType = "1"

        personPhoto = account.photoUrl.toString()
        //   showToast("Under Development")
        CheckSocialSignUpRepository(this@SignInActivity, this@SignInActivity)
                .callCheckSocialSignUpApi(socialId, socialType, "1")

        mGoogleSignInClient.signOut()
    }

    override fun onSuccessCheckSocialSignup(registrationResponse: UserDetialResponse) {
        session.setUserData(registrationResponse)
        Chatimmi.authorization = session.getAuthToken()
        Log.d("cjjxjkxjc", "onSuccessCheckSocialSignup:${session.getUserData()} ")
        if (registrationResponse.status.equals("success")) {
            if (registrationResponse.data?.social_status.equals("2")) {
                socialSignUpRepository(this@SignInActivity, this@SignInActivity).callSocialSignUpApi(socialName, socialEmail, "1", socialId, socialType, "jnkjn")
                session.setUserData(registrationResponse)
            } else {
                session.setIsUserLoggedIn("isLogin")
                session.setUserData(registrationResponse)
                val intent = Intent(this@SignInActivity, ChatimmiActivity::class.java)
                navigateTo(intent, true)
            }
        }
    }
    override fun onSuccessSocialSignup(registrationResponse: UserDetialResponse) {
        session.setIsUserLoggedIn("isLogin")
        session.setUserData(registrationResponse)
        Chatimmi.authorization = session.getAuthToken()
        val intent = Intent(this@SignInActivity, ChatimmiActivity::class.java)
        navigateTo(intent, true)
    }

    override fun onSuccessLogin(loginResponse: UserDetialResponse) {
    }

    override fun onShowBaseLoader() {
        showLoader()
    }
    override fun onHideBaseLoader() {
        hideLoader()
    }
    override fun onError(errorMessage: String) {
        toastMessage(errorMessage, this)
    }

}