package com.abank.IDCard.domain

import android.app.Application
import androidx.annotation.StringRes
import androidx.lifecycle.AndroidViewModel
import com.abank.IDCard.App
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel(application: Application) : AndroidViewModel(application) {
    private val compositeDisposable = CompositeDisposable()
    protected val context
        get() = getApplication<App>()

    protected fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    protected fun getString(@StringRes id: Int): String = context.getString(id)

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}