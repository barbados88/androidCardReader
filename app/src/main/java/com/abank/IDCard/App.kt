package com.abank.idcard

import android.app.Application
import com.abank.idcard.presentation.di.navigatorModule
import com.abank.idcard.presentation.di.viewModelModule
import com.crashlytics.android.Crashlytics
import com.example.data.di.dataModule
import io.fabric.sdk.android.Fabric
import org.koin.android.ext.android.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Fabric.with(this, Crashlytics())
        startKoin(this, listOf(viewModelModule, navigatorModule, dataModule))
    }

}