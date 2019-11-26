package com.example.data.auth.repository

import com.example.data.auth.datastore.AuthDataStore
import com.example.data.auth.model.LoginRequest
import com.example.data.auth.model.LoginResponse
import com.example.data.auth.model.LogoutRequest
import com.example.data.auth.model.OTPRequest
import com.example.data.prefs.SharedPreferencesProvider
import io.reactivex.Single

interface AuthRepository {

    fun authUser(body: LoginRequest): Single<LoginResponse>
    fun checkOTP(body: OTPRequest): Single<LoginResponse>
    fun logout(): Single<LoginResponse>
    fun storeToken(token: String)
    fun getToken(): String

}

class AuthRepositoryImpl(private val authDataStore: AuthDataStore,
                         private val sharedPreferencesProvider: SharedPreferencesProvider): AuthRepository {

    override fun authUser(body: LoginRequest) = authDataStore.auth(body)

    override fun checkOTP(body: OTPRequest) = authDataStore.checkOTP(body)

    override fun storeToken(token: String) = sharedPreferencesProvider.storeToken(token)

    override fun getToken() = sharedPreferencesProvider.getToken()

    override fun logout() = authDataStore.logout(LogoutRequest(getToken()))

}