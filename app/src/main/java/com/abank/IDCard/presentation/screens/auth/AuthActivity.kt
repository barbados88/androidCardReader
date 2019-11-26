package com.abank.idcard.presentation.screens.auth

import android.os.Bundle
import com.abank.idcard.R
import com.abank.idcard.presentation.base.BaseActivity
import com.abank.idcard.presentation.screens.auth.signin.SignInFragment

class AuthActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        if(savedInstanceState == null)
            supportFragmentManager.beginTransaction()
                    .replace(R.id.auth_container, SignInFragment())
                    .commit()
    }

}
