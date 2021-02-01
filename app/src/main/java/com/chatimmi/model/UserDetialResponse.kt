package com.chatimmi.model

data class UserDetialResponse(
	val data: Data? = null,
	val message: String? = null,
	val status: String? = null
)

data class Data(
	val userDetails: UserDetails? = null
)

data class UserDetails(
	val lastLoginAt: String? = null,
	val gender: Int? = null,
	val bio: Any? = null,
	val createdAt: String? = null,
	val language: String? = null,
	val userID: Int? = null,
	val userType: Int? = null,
	val alertCount: Int? = null,
	val updatedAt: String? = null,
	val isAvatarUrl: Int? = null,
	val signupFrom: Int? = null,
	val email: String? = null,
	val emailVerified: Int? = null,
	val avatar: String? = null,
	val contactNumber: Any? = null,
	val signupType: Int? = null,
	val token: String? = null,
	val countryCode: Any? = null,
	val fullName: String? = null,
	val pushAlertStatus: Int? = null,
	val emailVerificationToken: Int? = null,
	val dob: Any? = null,
	val passwordToken: Int? = null,
	val countryDial: Any? = null,
	val status: Int? = null
)

