package com.abank.idcard.presentation.base

import android.content.Context
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.transition.Fade
import androidx.transition.TransitionInflater
import androidx.transition.TransitionSet
import java.lang.ref.WeakReference

interface BaseNavigator {

    var fragmentManager: WeakReference<FragmentManager?>

    fun attachFragmentManager(fragmentManager: FragmentManager?) {
        this.fragmentManager = WeakReference(fragmentManager)
    }

    fun release() {
        this.fragmentManager = WeakReference(null)
    }

    fun Fragment.animateExit() {
        val exitFade = Fade()
        exitFade.duration = MOVE_DEFAULT_TIME
        exitTransition = exitFade
    }

    fun Fragment.animateEnter() {
        val enterFade = Fade()
        enterFade.duration = MOVE_DEFAULT_TIME
        enterFade.startDelay = FADE_DEFAULT_TIME
        enterTransition = enterFade
    }

    fun Fragment.animateShared(context: Context) {
        sharedElementEnterTransition = TransitionSet().apply {
            addTransition(TransitionInflater.from(context).inflateTransition(android.R.transition.move))
            duration = MOVE_DEFAULT_TIME
        }
    }

    fun FragmentManager.goWithAnimation(targetFragment: Fragment, container: Int, vararg sharedViews: View) {

        val previousFragment = findFragmentById(container) ?: return
        previousFragment.animateExit()

        targetFragment.animateShared(previousFragment.requireContext())
        //targetFragment.animateEnter()

        beginTransaction()
                .replace(container, targetFragment)
                .apply { sharedViews.forEach { addSharedElement(it, it.transitionName) } }
                .commit()

    }

    fun FragmentManager.goWithAnimationAndBackstack(targetFragment: Fragment, container: Int, vararg sharedViews: View, tag: String? = null) {

        val previousFragment = findFragmentById(container) ?: return
        previousFragment.animateExit()

        targetFragment.animateShared(previousFragment.requireContext())
        //targetFragment.animateEnter()

        beginTransaction()
                .replace(container, targetFragment, tag)
                .apply { sharedViews.forEach { addSharedElement(it, it.transitionName) } }
                .addToBackStack(null)
                .commit()

    }

    companion object {
        private const val MOVE_DEFAULT_TIME: Long = 700
        private const val FADE_DEFAULT_TIME: Long = 100
    }

}