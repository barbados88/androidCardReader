package com.example.data.di

import androidx.room.Room
import com.abank.idcard.data.repository.database.DataBase
import com.example.data.BuildConfig
import com.example.data.auth.api.AuthApi
import com.example.data.auth.datastore.AuthDataStore
import com.example.data.auth.datastore.AuthDataStoreImpl
import com.example.data.auth.repository.AuthRepository
import com.example.data.auth.repository.AuthRepositoryImpl
import com.example.data.prefs.SharedPreferencesProvider
import com.example.data.prefs.SharedPreferencesProviderImpl
import com.example.data.retrofit.RetrofitFactory
import com.example.data.retrofit.RetrofitFactoryImpl
import com.example.data.retrofit.RetrofitVariants
import com.example.data.retrofit.intercept.HeaderInterceptor
import com.example.data.scan.api.ScanApi
import com.example.data.scan.datastore.ScanDataStore
import com.example.data.scan.datastore.ScanDataStoreImpl
import com.example.data.scan.repository.ScanRepository
import com.example.data.scan.repository.ScanRepositoryImpl
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.module
import retrofit2.Retrofit

val dataModule = module {
    single<Gson> { GsonBuilder().setLenient().create() }
    single { OkHttpClient() }
    single<SharedPreferencesProvider> { SharedPreferencesProviderImpl(get()) }
    single { HeaderInterceptor(get()) }
    single<RetrofitFactory> { RetrofitFactoryImpl(get()) }
    single(RetrofitVariants.NO_AUTH.tag) { get<RetrofitFactory>().createRetrofit(get(), get(), RetrofitVariants.NO_AUTH) }
    single(RetrofitVariants.WITH_AUTH.tag) { get<RetrofitFactory>().createRetrofit(get(), get(), RetrofitVariants.WITH_AUTH) }

    single { Room.databaseBuilder(androidApplication().applicationContext, DataBase::class.java, BuildConfig.DB_NAME).build() }

    single { get<Retrofit>(RetrofitVariants.NO_AUTH.tag).create(AuthApi::class.java) }
    single { get<Retrofit>(RetrofitVariants.WITH_AUTH.tag).create(ScanApi::class.java) }

    single<AuthDataStore> { AuthDataStoreImpl(get()) }
    single<ScanDataStore> { ScanDataStoreImpl(get()) }

    single<AuthRepository> { AuthRepositoryImpl(get(), get()) }
    single<ScanRepository> { ScanRepositoryImpl(get()) }

}