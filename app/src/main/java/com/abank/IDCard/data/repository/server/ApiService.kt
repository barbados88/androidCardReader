package com.abank.IDCard.data.repository.server

import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @GET("")
    fun getCode(@Query("type") type: Int): Single<ResponseBody>

    @POST("login/reg")
    fun auth(): Single<ResponseBody>

    @GET("")
    fun register(): Single<ResponseBody>

}