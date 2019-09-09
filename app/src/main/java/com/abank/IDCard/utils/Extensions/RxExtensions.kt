package com.abank.IDCard.utils.Extensions

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

inline fun <reified T> Observable<T>.applyIoSchedulers(): Observable<T> =
        this.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

inline fun <reified T> Single<T>.applyIoSchedulers(): Single<T> =
        this.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

inline fun <reified T> Flowable<T>.applyIoSchedulers(): Flowable<T> =
        this.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

inline fun Completable.applyIoSchedulers(): Completable =
        this.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

fun <T> Flowable<T>.toLiveData(): LiveData<T> {
    return LiveDataReactiveStreams.fromPublisher(this)
}
