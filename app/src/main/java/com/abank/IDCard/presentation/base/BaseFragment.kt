package com.abank.IDCard.presentation.base

import android.app.ActionBar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.abank.IDCard.utils.Extensions.hideKeyboardEx
import com.abank.IDCard.utils.Extensions.showSnack
import com.abank.IDCard.utils.Extensions.showToast
import com.abank.IDCard.utils.illegalState

abstract class BaseFragment : Fragment() {

    protected val appBar: ActionBar? = activity?.actionBar

    protected fun disableHomeAsUp() = appBar?.setDisplayHomeAsUpEnabled(false)

    protected fun initializeNavigationBar(title: String, showBackButton: Boolean, @DrawableRes resId: Int? = null) {
        appBar?.apply {
            setTitle(title)
            this.setDisplayHomeAsUpEnabled(showBackButton)
            resId?.let { setHomeAsUpIndicator(it) }
            this.elevation = 4f
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> fragmentManager?.popBackStackImmediate()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(getLayout(), container, false)
        return view
    }

    fun getColor(@ColorRes colorRes: Int) = context?.let { ContextCompat.getColor(it, colorRes) }

    abstract fun getLayout(): Int

    protected fun showToast(text: String) = activity?.showToast(text)

    protected fun showSnack(text: String) = activity?.showSnack(text)

    protected fun hideKeyboard() = (activity as? AppCompatActivity)?.hideKeyboardEx()

    inline fun <reified T> argOrThrow(arg: String): T =
            argOrNull<T>(arg) ?: illegalState("Arg $arg is missing")

    inline fun <reified T> argOrNull(arg: String): T? = arguments?.get(arg) as? T

    fun getSupportActionBar() = (activity as? AppCompatActivity)?.supportActionBar

}
