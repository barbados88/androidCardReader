package com.abank.idcard.data.repository.server.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class BaseHeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var original = chain.request()
        val requestBuilder = original.newBuilder()
        //val token = App.applicationContext.getShared(AUTHORIZATION_TOKEN, "")
        //requestBuilder.header("Authorization", token)
        requestBuilder.header("Cache-Control", "true")
        requestBuilder.header("Content-Type", "raw")
        original = requestBuilder.build()
        return chain.proceed(original)
    }
}