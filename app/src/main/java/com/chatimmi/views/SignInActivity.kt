package com.chatimmi.views

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.WindowManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.chatimmi.R
import com.chatimmi.app.utils.UIStateManager
import com.chatimmi.app.utils.showToast
import com.chatimmi.base.BaseActivitykt
import com.chatimmi.databinding.ActivitySignInBinding
import com.chatimmi.fragmentchatimmi.ChatimmiActivity
import com.chatimmi.model.LoginRegistrationResponse
import com.chatimmi.repository.SignInRepository
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
import org.json.JSONObject

@Suppress("DEPRECATION")
class SignInActivity : BaseActivitykt() {
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val signInRepository = SignInRepository()
        val factory = SignInViewModalFactory(signInRepository)
        viewModel = ViewModelProviders.of(this, factory).get(SignInViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in)
        binding?.lifecycleOwner = this
        binding!!.signInViewModel = viewModel
        binding?.invalidateAll()
        /*    viewModel!!.getLoginResponseLiveData()?.observe(this@SignInActivity, Observer<Any?> { volumesResponse ->
                if (volumesResponse != null) {
                    toastMessage(getString(R.string.done), this)
                }
            })*/

        val w = window
        w.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        if (packageName.equals("com.chatimmi")) {
            gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build()
        } else {
            gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build()
        }
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        callbackManager = CallbackManager.Factory.create()
        /*      binding!!.btnSignup.setOnClickListener {
                  if (validate()) {
                      //showToast("Under Development")

                      val intent = Intent(this@SignInActivity, ChatimmiActivity::class.java)
                      //  intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                      // startActivity(intent)
                      navigateTo(intent, true)
                  }
              }*/
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
            /*  val intent = Intent(this@SignInActivity, ChatimmiActivity::class.java)*/
            /*    navigateTo(intent,true)*/
            showToast("Under Development")
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

                        val getData = it.data as LoginRegistrationResponse
                        Log.d("fbasfbjasfa", "onCreate: $getData")
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
            showToast("Under Development")
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
                        } else {
                            socialEmail = ""
                        }

                        socialId = obj.getString("id")
                        socialType = "2"
                        val personPhoto1 = obj.getJSONObject("picture")
                        val data = personPhoto1.getJSONObject("data")
                        personPhoto = data.getString("url")

                        /*CheckSocialSignUpPresenter(this@LoginActivity, this@LoginActivity)
                                     .callCheckSocialSignUpApi(
                                             getDeviceId(),
                                             "2",
                                             getDeviceTimeZone(),
                                             socialEmail,
                                             socialId,
                                             socialType,
                                             "buyer",
                                             tokenGlobal
                                     )*/
                        LoginManager.getInstance().logOut()
                    }
                })

                val parameters = Bundle()
                parameters.putString("fields", "name,email,id,picture")
                request.parameters = parameters
                request.executeAsync()
            }

            override fun onCancel() {
            }

            override fun onError(error: FacebookException?) {
            }
        })
    }

    fun handleSignInResult(account: GoogleSignInAccount) {
        if (account.displayName != null)
            socialName = account.displayName!!
        else
            socialName = ""

        if (account.email != null)
            socialEmail = account.email!!
        else
            socialEmail = ""

        if (account.id != null)
            socialId = account.id!!
        else
            socialId = ""

        socialType = "1"

        personPhoto = account.photoUrl.toString()
        showToast("Under Development")
        /*  CheckSocialSignUpPresenter(this@LoginActivity, this@LoginActivity)
                  .callCheckSocialSignUpApi(
                          getDeviceId(), "2", getDeviceTimeZone(), socialEmail, socialId, socialType,
                          "buyer", tokenGlobal
                  )*/

        mGoogleSignInClient.signOut()
    }

    /*  private fun validate(): Boolean {


          val email = binding!!.etEmail.text.toString()
          val password = binding!!.etPassword.text.toString()


          if ("" == email) {
              Toast.makeText(this, getString(R.string.please_enter_email), Toast.LENGTH_SHORT).show();
              return false
          }
          if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
              Toast.makeText(this, getString(R.string.please_enter_valid_email), Toast.LENGTH_SHORT).show();
              return false
          }
          if ("" == password) {
              Toast.makeText(this, getString(R.string.please_enter_password), Toast.LENGTH_SHORT).show();
              return false
          }
          if (password.length < 6) {
              Toast.makeText(this, getString(R.string.please_enter_password_with_minimum), Toast.LENGTH_SHORT).show();
              return false
          }


          return true
      }*/
}