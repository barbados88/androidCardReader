package com.abank.idcard.presentation.screens.scan.passportdetails

import androidx.fragment.app.FragmentManager
import com.abank.idcard.R
import com.abank.idcard.presentation.base.BaseNavigator
import com.abank.idcard.presentation.screens.scan.select.SelectFragment
import java.lang.ref.WeakReference

interface PassportDetailsNavigator : BaseNavigator {

    fun goSelect()

}

class PassportDetailsNavigatorImpl : PassportDetailsNavigator {

    override var fragmentManager: WeakReference<FragmentManager?> = WeakReference(null)

    override fun goSelect() {
        fragmentManager.get()?.goWithAnimation(SelectFragment.newInstance(), R.id.scan_container)
    }

}