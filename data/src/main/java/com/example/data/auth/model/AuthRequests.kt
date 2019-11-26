package com.example.data.auth.model

import com.example.data.BuildConfig
import com.google.gson.annotations.SerializedName

data class LoginRequest(
        @SerializedName("phone") val login: String,
        @SerializedName("password") val password: String,
        @SerializedName("request_ref")val ref: String = BuildConfig.API_KEY)

data class OTPRequest(
        @SerializedName("token") val token: String,
        @SerializedName("password") val otp: String)

data class LogoutRequest(@SerializedName("token") val token: String)