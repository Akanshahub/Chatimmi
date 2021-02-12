package com.chatimmi.model

data class ChangePasswordResponse(
    val status: String,
    val message: String,
    val `data`: Data
) {
    class Data(
    )
}