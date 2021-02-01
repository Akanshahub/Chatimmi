package com.chatimmi.model

data class LogoutResponse(
        val `data`: DataLogout,
        val message: String,
        val status: String,
        val status_code: Int
)

class DataLogout(
)