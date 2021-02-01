package com.chatimmi.model

data class ResetPasswordResponse(
        val `data`: DataRessetPassword,
        val message: String,
        val status: String,
        val status_code: Int
)

class DataRessetPassword(
)