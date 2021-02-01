package com.chatimmi.retrofitnetwork

import com.chatimmi.model.LoginRegistrationResponse
import com.chatimmi.model.LogoutResponse
import com.chatimmi.model.ResetPasswordResponse

import com.chatimmi.model.UserDetialResponse

interface ApiCallback {

    interface LoginCallback: BaseInterface {
        fun onSuccessLogin(loginResponse : LoginRegistrationResponse)
    }

    interface SignUpCallback: BaseInterface {
        fun onSuccessRegistration(userDetialResponse: UserDetialResponse )
    }
    interface ResetPasswordCallback: BaseInterface {
        fun onSuccessForgotPassword(resetPasswordResponse: ResetPasswordResponse)
    }

    interface LogoutCallback: BaseInterface {
        fun onSuccessLogout(logoutResponse: LogoutResponse)
        fun onTokenChangeError(message: String)
    }

}