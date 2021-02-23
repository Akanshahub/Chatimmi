package com.chatimmi.model

data class UserDetialResponse(
	val data: Data? = null,
	val message: String? = null,
	val status: String? = null
)

data class Data(
		val user_details: UserDetails,

		val social_status: String
)

data class UserDetails(
	val last_login_at: String? = null,
	val updated_at: String? = null,
	val created_at: String? = null,
	val gender: Int? = null,
	val bio: Any? = null,
	val createdAt: String? = null,
	val language: String? = null,
	val userID: Int? = null,
	val user_type: Int? = null,
	val is_avatar_url: Int? = null,
	val alert_count: Int? = null,
	val updatedAt: String? = null,
	val isAvatarUrl: Int? = null,
	val signup_from: Int? = null,
	val email: String? = null,
	val email_verified: Int? = null,
	val avatar: String? = null,
	val contact_number: Any? = null,
	val signup_type: Int? = null,
	val token: String? = null,
	val country_code: Any? = null,
	val full_name: String? = null,
	var push_alert_status: String? = null,
	val email_verification_token: Int? = null,
	val dob: Any? = null,
	val password_token: Int? = null,
	val password: String? = null,
	val country_dial: Any? = null,
	val status: Int? = null
)

