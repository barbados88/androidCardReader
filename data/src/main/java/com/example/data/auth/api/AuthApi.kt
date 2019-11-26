package com.example.data.auth.api

import com.example.data.auth.model.LoginRequest
import com.example.data.auth.model.LoginResponse
import com.example.data.auth.model.LogoutRequest
import com.example.data.auth.model.OTPRequest
import com.example.data.network.NetworkContract
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST(NetworkContract.Auth.LOGIN)
    fun auth(@Body body: LoginRequest): Single<LoginResponse>

    @POST(NetworkContract.Auth.VERIFY_OTP)
    fun checkOTP(@Body body: OTPRequest): Single<LoginResponse>

    @POST(NetworkContract.Auth.LOGOUT)
    fun logout(@Body body: LogoutRequest): Single<LoginResponse>

}