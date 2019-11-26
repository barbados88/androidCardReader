package com.abank.idcard.presentation.screens.scan.scanner

import androidx.fragment.app.FragmentManager
import com.abank.idcard.presentation.base.BaseNavigator
import com.abank.idcard.utils.Extensions.findAttached
import com.abank.idcard.presentation.screens.scan.container.ContainerFragment
import org.jetbrains.anko.bundleOf
import java.lang.ref.WeakReference

interface ScannerNavigator : BaseNavigator {

    fun backWithResult(result: String)

}

class ScannerNavigatorImpl : ScannerNavigator {

    override var fragmentManager: WeakReference<FragmentManager?> = WeakReference(null)

    override fun backWithResult(result: String) {
        val cont = fragmentManager.get()?.findAttached<ContainerFragment>(ContainerFragment.TAG)
        cont?.arguments?.putString(ContainerFragment.RECOGNIZED_TEXT, result) ?: run { cont?.arguments = bundleOf(ContainerFragment.RECOGNIZED_TEXT to result)  }
        fragmentManager.get()?.popBackStack()
    }

}