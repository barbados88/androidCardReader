package com.abank.idcard.data.repository.server

import com.abank.idcard.data.repository.server.pojo.request.DataRequest
import com.abank.idcard.data.repository.server.pojo.request.LoginRequest
import com.abank.idcard.data.repository.server.pojo.request.LogoutRequest
import com.abank.idcard.data.repository.server.pojo.request.OTPRequest
import com.abank.idcard.data.repository.server.pojo.response.LoginResponse
import com.abank.idcard.data.repository.server.pojo.response.TransactionsResponse
import io.reactivex.Single
import retrofit2.http.*

interface ApiService {

    @POST("api/auth/login")
    fun auth(@Body body: LoginRequest): Single<LoginResponse>

    @POST("api/auth/otp/verify")
    fun checkOTP(@Body body: OTPRequest): Single<LoginResponse>

    @POST("api/auth/LOGOUT")
    fun logout(@Body body: LogoutRequest): Single<LoginResponse>

    @GET("api/v1/list/requests")// /ab190291dvs
    fun getOrders(@HeaderMap headers: Map<String, String>): Single<TransactionsResponse>

    @POST("api/v1/id-card/send")
    fun sendData(@HeaderMap headers: Map<String, String>,
                 @Body body: DataRequest): Single<LoginResponse>

}