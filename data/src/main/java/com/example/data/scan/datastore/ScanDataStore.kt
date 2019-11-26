package com.example.data.scan.datastore

import com.example.data.auth.model.LoginResponse
import com.example.data.scan.api.ScanApi
import com.example.data.scan.model.DataRequest
import com.example.data.scan.model.ProfileResponse
import com.example.data.scan.model.TransactionsResponse
import io.reactivex.Single

interface ScanDataStore {

    fun getOrders(): Single<TransactionsResponse>
    fun sendData(data: DataRequest): Single<LoginResponse>
    fun getUserInfo(): Single<ProfileResponse>

}

class ScanDataStoreImpl(private val scanApi: ScanApi): ScanDataStore {

    override fun getOrders() = scanApi.getOrders()

    override fun sendData(data: DataRequest) = scanApi.sendData(data)

    override fun getUserInfo() = scanApi.getUserInfo()

}