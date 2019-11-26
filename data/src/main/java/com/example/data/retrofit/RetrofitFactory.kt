package com.example.data.retrofit

import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Retrofit

interface RetrofitFactory {

    fun createRetrofit(okHttpClient: OkHttpClient, gson: Gson, variants: RetrofitVariants): Retrofit

}

enum class RetrofitVariants(val tag: String) {
    WITH_AUTH("with_auth"),
    NO_AUTH("no_auth")
}