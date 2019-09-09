package com.abank.IDCard.koin

import android.content.Context
import com.abank.IDCard.BuildConfig
import com.abank.IDCard.data.repository.database.DataBase
import com.abank.IDCard.data.repository.server.ApiService
import com.abank.IDCard.data.repository.server.ServerCommunicator
import com.abank.IDCard.data.repository.server.interceptor.BaseAuthenticator
import com.abank.IDCard.data.repository.server.interceptor.BaseHeaderInterceptor
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.Module
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val repositoryModule: Module = module {
    single { provideDatabase(androidApplication().applicationContext) }
    single { provideServerCommunicator() }
    single { provideSharedPrefs(androidApplication().applicationContext) }
}

private fun provideSharedPrefs(context: Context) = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)

private fun provideDatabase(context: Context): DataBase {
    return DataBase.getInstance(context)!!
}

// TODO: - change to real api url

private const val API_URL = "https://path/to/your/api/"

private fun provideServerCommunicator(): ServerCommunicator {

    val okHttpClientBuilder = OkHttpClient.Builder()
            .connectionPool(ConnectionPool(5, 30, TimeUnit.SECONDS))
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(BaseHeaderInterceptor())
            .authenticator(BaseAuthenticator())
    if (BuildConfig.DEBUG) {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        okHttpClientBuilder.addInterceptor(httpLoggingInterceptor)
    }
    val retrofitBuilder = Retrofit.Builder()
            .client(okHttpClientBuilder.build())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())

    val retrofit = retrofitBuilder.baseUrl(API_URL).build()
    val apiService = retrofit.create<ApiService>(ApiService::class.java)
    return ServerCommunicator(apiService)

}




