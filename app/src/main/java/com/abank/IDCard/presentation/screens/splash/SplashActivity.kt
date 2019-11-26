package com.abank.idcard.presentation.screens.splash

import android.content.Intent
import android.os.Bundle
import com.abank.idcard.presentation.screens.auth.AuthActivity
import com.abank.idcard.presentation.base.BaseActivity
import com.abank.idcard.utils.Extensions.bindDataTo
import com.abank.idcard.presentation.screens.scan.ScanActivity
import org.koin.android.ext.android.get

class SplashActivity : BaseActivity() {

    private val viewModel: SplashViewModel = get()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.checkAuthentication()
        subscribeLiveData()
    }

    override fun onResume() {
        super.onResume()
        viewModel.checkAuthentication()
    }

    private fun subscribeLiveData() {
        bindDataTo(viewModel.isAuthenticatedLiveData) {
            val intent = Intent(this, if(it) ScanActivity::class.java else AuthActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
