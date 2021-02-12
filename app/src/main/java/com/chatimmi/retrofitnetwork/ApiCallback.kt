package com.chatimmi.retrofitnetwork

import com.chatimmi.model.LogoutResponse
import com.chatimmi.model.ResetPasswordResponse

import com.chatimmi.model.UserDetialResponse
import com.chatimmi.usermainfragment.group.filter.filtercategorygroup.GroupFilterResponse
import com.chatimmi.usermainfragment.group.immigration.GroupListResponse

interface ApiCallback {

    interface LoginCallback: BaseInterface {
        fun onSuccessLogin(loginResponse : UserDetialResponse)
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
    interface SocialSignupCallback: BaseInterface {
        fun onSuccessSocialSignup(registrationResponse : UserDetialResponse)
    }

    interface CheckSocialSignupCallback: BaseInterface {
        fun onSuccessCheckSocialSignup(registrationResponse : UserDetialResponse)
    }
    interface grouplist: BaseInterface {
        fun onSuccessLogin(deliveryListResponse: GroupListResponse)
    }
    interface GroupFilterlist: BaseInterface {
        fun onSuccessLogin(deliveryListResponse: GroupFilterResponse)
    }
}