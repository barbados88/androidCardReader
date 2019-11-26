package com.abank.idcard.presentation.base

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
import com.abank.idcard.utils.Extensions.hideKeyboardEx
import com.abank.idcard.utils.Extensions.showSnack
import com.abank.idcard.utils.Extensions.showToast
import com.abank.idcard.utils.illegalState
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseFragment : Fragment() {

    abstract val navigator: BaseNavigator

    protected val compositeDisposable = CompositeDisposable()

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigator.attachFragmentManager(fragmentManager)
    }

    override fun onDestroy() {
        super.onDestroy()
        navigator.release()
        compositeDisposable.dispose()
    }

    protected fun Disposable.addtoBag() = compositeDisposable.add(this)

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
