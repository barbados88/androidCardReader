package com.abank.idcard.presentation.screens.scan.select

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import com.abank.idcard.R
import com.abank.idcard.presentation.screens.auth.AuthActivity
import com.abank.idcard.presentation.base.BaseNavigator
import com.abank.idcard.presentation.screens.scan.container.ContainerFragment
import java.lang.ref.WeakReference

interface SelectNavigator : BaseNavigator {

    fun logout(context: Context)
    fun goContainer(orderId: String, vararg sharedViews: View)
}

class SelectNavigatorImpl : SelectNavigator {

    override var fragmentManager: WeakReference<FragmentManager?> = WeakReference(null)

    override fun logout(context: Context) {
        val intent = Intent(context, AuthActivity::class.java)
        ContextCompat.startActivity(context, intent, null)
    }

    override fun goContainer(orderId: String, vararg sharedViews: View) {
        fragmentManager.get()?.goWithAnimationAndBackstack(ContainerFragment.newInstance(orderId), R.id.scan_container, *sharedViews, tag = ContainerFragment.TAG)
    }

}