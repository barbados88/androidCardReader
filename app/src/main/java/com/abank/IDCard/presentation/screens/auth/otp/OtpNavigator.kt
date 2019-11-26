package com.abank.idcard.presentation.screens.auth.otp

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.FragmentManager
import com.abank.idcard.presentation.base.BaseNavigator
import com.abank.idcard.presentation.fragment.IntentData
import com.abank.idcard.presentation.screens.scan.ScanActivity
import java.lang.ref.WeakReference

interface OtpNavigator : BaseNavigator {

    fun goSelect(context: Context, token: String)

}

class OtpNavigatorImpl : OtpNavigator {

    override var fragmentManager: WeakReference<FragmentManager?> = WeakReference(null)

    override fun goSelect(context: Context, token: String) {
        val intent = Intent(context, ScanActivity::class.java)
        intent.putExtra(IntentData.OTP_TOKEN, token)
        startActivity(context, intent, null)
    }

}