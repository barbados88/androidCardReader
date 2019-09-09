package com.abank.IDCard

import android.app.Application
import android.content.Context
import com.crashlytics.android.Crashlytics
import com.abank.IDCard.koin.repositoryModule
import com.abank.IDCard.koin.viewModelModule
import io.fabric.sdk.android.Fabric
import org.koin.android.ext.android.startKoin

class App : Application() {

    init {
        instance = this
    }

    companion object {
        var instance: App? = null
        val applicationContext : Context
            get() = instance!!.applicationContext
    }

    override fun onCreate() {
        super.onCreate()
        Fabric.with(this, Crashlytics())
        startKoin(this, listOf(viewModelModule, repositoryModule))
    }
}