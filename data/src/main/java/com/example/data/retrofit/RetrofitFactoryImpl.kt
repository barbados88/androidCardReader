package com.example.data.retrofit

import com.example.data.BuildConfig
import com.example.data.retrofit.intercept.HeaderInterceptor
import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitFactoryImpl(private val headerInterceptor: HeaderInterceptor): RetrofitFactory {

    override fun createRetrofit(okHttpClient: OkHttpClient, gson: Gson, variants: RetrofitVariants): Retrofit {

        val okHttpBuilder =
                okHttpClient.newBuilder()

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        okHttpBuilder.addInterceptor(loggingInterceptor)

        if(variants == RetrofitVariants.WITH_AUTH) okHttpBuilder.addInterceptor(headerInterceptor)

        val builder = Retrofit.Builder()
                .baseUrl(BuildConfig.API_URL)
                .client(okHttpBuilder.build())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())

        return builder.build()

    }

}