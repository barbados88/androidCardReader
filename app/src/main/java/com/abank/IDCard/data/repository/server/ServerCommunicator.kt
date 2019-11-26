package com.abank.idcard.data.repository.server

import com.abank.idcard.data.repository.server.pojo.request.DataRequest
import com.abank.idcard.data.repository.server.pojo.request.LoginRequest
import com.abank.idcard.data.repository.server.pojo.request.LogoutRequest
import com.abank.idcard.data.repository.server.pojo.request.OTPRequest
import com.abank.idcard.data.repository.server.pojo.response.LoginResponse
import com.abank.idcard.data.repository.server.pojo.response.TransactionsResponse
import io.reactivex.ObservableTransformer
import io.reactivex.Single
import io.reactivex.SingleTransformer
import io.reactivex.schedulers.Schedulers

// TODO: - create correct headers to use in requests

const val AUTHORIZATION_TOKEN = "AUTHORIZATION_TOKEN"

fun headers(): Map<String, String> {
    val map = hashMapOf<String, String>()
    //map["Authorization"] = "Bearer " + App.applicationContext.getShared("access_token", "")
    return map
}

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

    fun auth(body: LoginRequest): Single<LoginResponse> = apiService.auth(body).compose(singleTransformer())

    fun checkOTP(body: OTPRequest): Single<LoginResponse> = apiService.checkOTP(body).compose(singleTransformer())

    fun logout(body: LogoutRequest): Single<LoginResponse> = apiService.logout(body).compose(singleTransformer())

    fun getOrders(): Single<TransactionsResponse> = apiService.getOrders(headers()).compose(singleTransformer())

    fun sendData(body: DataRequest): Single<LoginResponse> = apiService.sendData(headers(), body).compose(singleTransformer())

}