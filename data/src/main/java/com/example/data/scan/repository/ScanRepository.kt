package com.example.data.scan.repository

import com.example.data.auth.model.LoginResponse
import com.example.data.scan.datastore.ScanDataStore
import com.example.data.scan.model.DataRequest
import com.example.data.scan.model.ProfileResponse
import com.example.data.scan.model.TransactionsResponse
import io.reactivex.Single

interface ScanRepository {

    fun getOrders(): Single<TransactionsResponse>
    fun sendData(data: DataRequest): Single<LoginResponse>
    fun getUserInfo(): Single<ProfileResponse>

}

class ScanRepositoryImpl(private val scanDataStore: ScanDataStore) : ScanRepository {

    override fun getOrders() = scanDataStore.getOrders()

    override fun sendData(data: DataRequest) = scanDataStore.sendData(data)

    override fun getUserInfo() = scanDataStore.getUserInfo()

}