package com.abank.IDCard.data.repository.server.interceptor

import com.abank.IDCard.App
import com.abank.IDCard.utils.getShared
import okhttp3.Interceptor
import okhttp3.Response

class BaseHeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var original = chain.request()
        val requestBuilder = original.newBuilder()
        val token = App.applicationContext.getShared("token", "")
        requestBuilder.header("Authorization", token)
        requestBuilder.header("Cache-Control", "true")
        requestBuilder.header("Content-Type", "raw")
        original = requestBuilder.build()
        return chain.proceed(original)
    }
}