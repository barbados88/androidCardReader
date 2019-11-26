package com.abank.idcard.presentation.screens.auth.signin

import android.view.View
import androidx.fragment.app.FragmentManager
import com.abank.idcard.R
import com.abank.idcard.presentation.screens.auth.otp.OtpFragment
import com.abank.idcard.presentation.base.BaseNavigator
import java.lang.ref.WeakReference

interface SignInNavigator: BaseNavigator {

    fun goOtp(token: String, vararg sharedViews: View)

}

class SignInNavigatorImpl : SignInNavigator {

    override var fragmentManager: WeakReference<FragmentManager?> = WeakReference(null)

    override fun goOtp(token: String, vararg sharedViews: View) {
        fragmentManager.get()?.goWithAnimation(OtpFragment.newInstance(token), R.id.auth_container, *sharedViews)
    }

}