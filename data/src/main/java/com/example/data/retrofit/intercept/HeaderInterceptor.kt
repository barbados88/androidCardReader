package com.example.data.retrofit.intercept

import com.example.data.prefs.SharedPreferencesProvider
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor(private val sharedPreferencesProvider: SharedPreferencesProvider) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response =
        chain.request()
                .newBuilder()
                .addHeader(AUTHORIZATION_KEY, getToken())
                .build()
                .let { chain.proceed(it) }

    private fun getToken() = BEARER_PREFIX.format(sharedPreferencesProvider.getToken())

    companion object {
        private const val AUTHORIZATION_KEY = "Authorization"
        private const val BEARER_PREFIX = "Bearer %s"
    }

}