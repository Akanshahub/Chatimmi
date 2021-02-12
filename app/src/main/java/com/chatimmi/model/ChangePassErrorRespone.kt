package com.chatimmi.model

data class ChangePassErrorRespone(
    val status: String,
    val status_code: Int,
    val error_type: String,
    val message: String,
    val error_details: List<ErrorDetail>,
    val `data`: Data
) {
    data class ErrorDetail(
        val message: String,
        val `param`: String
    )

    class Data(
    )
}