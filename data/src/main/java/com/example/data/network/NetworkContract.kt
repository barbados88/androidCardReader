package com.example.data.network

object NetworkContract {

    private const val API = "api"

    object Auth {
        private const val AUTH = "${API}/auth"
        const val LOGIN = "${AUTH}/login"
        const val VERIFY_OTP = "${AUTH}/otp/verify"
        const val LOGOUT = "${AUTH}/logout"
    }

    object Scan {
        private const val V1 = "${API}/v1"
        const val USER_INFO = "${V1}/user-info"
        const val ORDERS = "${V1}/list/requests"
        const val SEND_DATA = "${V1}/id-card/send"
    }

}