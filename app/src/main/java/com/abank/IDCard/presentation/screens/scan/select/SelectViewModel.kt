package com.abank.idcard.presentation.screens.scan.select

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.abank.idcard.presentation.base.BaseViewModel
import com.abank.idcard.presentation.screens.scan.select.model.ProfileModel
import com.abank.idcard.presentation.screens.scan.select.model.toProfile
import com.example.data.auth.repository.AuthRepository
import com.example.data.scan.model.TransactionSingle
import com.example.data.scan.repository.ScanRepository

abstract class SelectViewModel : BaseViewModel() {

    abstract val ordersLiveData: LiveData<List<TransactionSingle>>
    abstract val errorLiveData: LiveData<String>
    abstract val logoutLiveData: LiveData<Unit>
    abstract val profileInfoLiveData: LiveData<ProfileModel>

    abstract fun loadOrders()
    abstract fun loadProfile()
    abstract fun logout()

}

class SelectViewModelImpl(private val scanRepository: ScanRepository,
                          private val authRepository: AuthRepository) : SelectViewModel() {

    override val ordersLiveData: MutableLiveData<List<TransactionSingle>> = MutableLiveData()
    override val errorLiveData: MutableLiveData<String> = MutableLiveData()
    override val logoutLiveData: MutableLiveData<Unit> = MutableLiveData()
    override val profileInfoLiveData: MutableLiveData<ProfileModel> = MutableLiveData()

    override fun loadProfile() {
        scanRepository.getUserInfo().execute(errorLiveData) {
            profileInfoLiveData.postValue(it.toProfile())
        }
    }

    override fun loadOrders() {
        scanRepository.getOrders().execute(errorLiveData) {
            ordersLiveData.postValue(it.transactions)
        }
    }

    override fun logout() {
        authRepository.logout().execute(errorLiveData) {
            authRepository.storeToken("")
            logoutLiveData.postValue(Unit)
        }
    }

}