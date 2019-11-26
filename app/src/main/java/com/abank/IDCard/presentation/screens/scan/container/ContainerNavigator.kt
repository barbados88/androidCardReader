package com.abank.idcard.presentation.screens.scan.container

import com.abank.idcard.presentation.screens.scan.nfcreader.NFCReaderActivity
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.FragmentManager
import com.abank.idcard.R
import com.abank.idcard.presentation.base.BaseNavigator
import com.abank.idcard.presentation.screens.scan.nfcreader.NfcReaderFragment
import com.abank.idcard.presentation.screens.scan.scanner.ScannerFragment
import org.jmrtd.lds.icao.MRZInfo
import java.lang.ref.WeakReference

interface ContainerNavigator : BaseNavigator {

    fun toScanner()

    fun toNfcReader(context: Context, mrzInfo: MRZInfo, orderId: String, track: String)

}

class ContainerNavigatorImpl : ContainerNavigator {

    override var fragmentManager: WeakReference<FragmentManager?> = WeakReference(null)

    override fun toScanner() {
        fragmentManager.get()?.goWithAnimationAndBackstack(ScannerFragment.newInstance(), R.id.scan_container)
    }

    override fun toNfcReader(context: Context, mrzInfo: MRZInfo, orderId: String, track: String) {
        val intent = Intent(context, NFCReaderActivity::class.java)
        intent.putExtra(NfcReaderFragment.IntentData.KEY_MRZ_INFO, mrzInfo)
        intent.putExtra(NfcReaderFragment.IntentData.ORDER_ID, orderId)
        intent.putExtra(NfcReaderFragment.IntentData.TRACK, track)
        startActivity(context, intent, null)
    }

}