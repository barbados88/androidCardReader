package com.example.data.auth.datastore

import com.example.data.auth.api.AuthApi
import com.example.data.auth.model.LoginRequest
import com.example.data.auth.model.LoginResponse
import com.example.data.auth.model.LogoutRequest
import com.example.data.auth.model.OTPRequest
import io.reactivex.Single
import io.reactivex.SingleTransformer
import io.reactivex.schedulers.Schedulers

interface AuthDataStore {

    fun auth(body: LoginRequest): Single<LoginResponse>
    fun checkOTP(body: OTPRequest): Single<LoginResponse>
    fun logout(logoutRequest: LogoutRequest): Single<LoginResponse>

}

class AuthDataStoreImpl(private val authApi: AuthApi): AuthDataStore {

    override fun auth(body: LoginRequest): Single<LoginResponse> = authApi.auth(body)

    override fun checkOTP(body: OTPRequest): Single<LoginResponse> = authApi.checkOTP(body)

    override fun logout(logoutRequest: LogoutRequest): Single<LoginResponse> = authApi.logout(logoutRequest)

}