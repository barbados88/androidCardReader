package com.example.data.auth.model

import com.google.gson.annotations.SerializedName

data class LoginResponse(@SerializedName("token") val authToken: String?,
                         @SerializedName("token_type") val tokenType: String?,
                         @SerializedName("expires_in") val expireDate: String?,
                         @SerializedName("otpSend") val isOtpSent: Boolean? = false,
                         @SerializedName("request_ref") val requestRef: String?,
                         @SerializedName("response_ref") val responseRef: String?,
                         @SerializedName("timestamp") val timestamp: String?,
                         @SerializedName("result") val result: String,
                         @SerializedName("error") val error: ReaderError? = null) {

    val token: String
        get() = authToken ?: ""

}

data class ReaderError(@SerializedName("status") var status: String,
                       @SerializedName("msg") var msgError: String? = null,
                       @SerializedName("title") var titleError: String? = null): Throwable() {

    val errorMessage: String
        get() {
            return msgError ?: titleError ?: "Unknown format of server error"
        }

}