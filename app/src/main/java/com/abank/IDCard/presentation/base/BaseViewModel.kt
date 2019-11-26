package com.abank.idcard.presentation.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.abank.idcard.data.repository.server.pojo.response.ErrorHelper
import com.abank.idcard.utils.Extensions.applySchedulers
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    protected fun <T> Single<T>.execute(errorLiveData: MutableLiveData<String>, onResult: (T) -> Unit) =
            applySchedulers().subscribe({ onResult.invoke(it) }, { errorLiveData.postValue(ErrorHelper.parseErrorAndGetString(it)) }).also { compositeDisposable.add(it) }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}