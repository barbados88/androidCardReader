package com.example.data.scan.api

import com.example.data.auth.model.LoginResponse
import com.example.data.network.NetworkContract
import com.example.data.scan.model.DataRequest
import com.example.data.scan.model.ProfileResponse
import com.example.data.scan.model.TransactionsResponse
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.POST

interface ScanApi {

    @GET(NetworkContract.Scan.ORDERS)// /ab190291dvs
    fun getOrders(): Single<TransactionsResponse>

    @POST(NetworkContract.Scan.SEND_DATA)
    fun sendData(@Body body: DataRequest): Single<LoginResponse>

    @GET(NetworkContract.Scan.USER_INFO)
    fun getUserInfo(): Single<ProfileResponse>

}