package com.abank.idcard.presentation.screens.scan.nfcreader

import android.graphics.Bitmap
import androidx.fragment.app.FragmentManager
import com.abank.idcard.R
import com.abank.idcard.presentation.base.BaseNavigator
import com.abank.idcard.presentation.screens.scan.passportdetails.PassportDetailsFragment
import com.abank.idcard.presentation.fragment.PassportPhotoFragment
import com.abank.idcard.utils.NFC.Passport
import java.lang.ref.WeakReference

interface NfcReaderNavigator : BaseNavigator {

    fun goPassport(passport: Passport)

    fun goPhoto(bitmap: Bitmap)

}

class NfcReaderNavigatorImpl : NfcReaderNavigator {

    override var fragmentManager: WeakReference<FragmentManager?> = WeakReference(null)

    override fun goPassport(passport: Passport) {
        fragmentManager.get()?.goWithAnimation(PassportDetailsFragment.newInstance(passport), R.id.container)
    }

    override fun goPhoto(bitmap: Bitmap) {
        fragmentManager.get()?.goWithAnimation(PassportPhotoFragment.newInstance(bitmap), R.id.scan_container)
    }
}