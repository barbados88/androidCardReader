package com.abank.IDCard.presentation.activity.splash

import android.content.Intent
import android.os.Bundle
import com.abank.IDCard.presentation.activity.scan.ContainerActivity
import com.abank.IDCard.presentation.base.BaseActivity

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        scannerActivity()
        finish()
    }

    override fun onResume() {
        super.onResume()
        scannerActivity()
        finish()
    }

    private fun scannerActivity() {
        val intent = Intent(this, ContainerActivity::class.java)
        startActivity(intent)
    }
}
