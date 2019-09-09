package com.abank.IDCard.data.repository.server

import io.reactivex.ObservableTransformer
import io.reactivex.Single
import io.reactivex.SingleTransformer
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody

// TODO: - create correct headers to use in requests

class ServerCommunicator(private val apiService: ApiService) {

    companion object {
        private const val DEFAULT_TIMEOUT = 30 // seconds
        private const val DEFAULT_RETRY_ATTEMPTS: Long = 4
    }

    private fun <T> observableTransformer(): ObservableTransformer<T, T> = ObservableTransformer {
        it.subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
    }


    private fun <T> singleTransformer(): SingleTransformer<T, T> = SingleTransformer {
        it.subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
    }

    // TODO: create correct response body object if needed

    fun getCode(type: Int): Single<ResponseBody> = apiService.getCode(type)

    fun auth(): Single<ResponseBody> = apiService.auth().compose(singleTransformer())

    fun register(): Single<ResponseBody> = apiService.register().compose(singleTransformer())

}