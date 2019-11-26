package com.abank.idcard.presentation.screens.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.abank.idcard.presentation.base.BaseViewModel
import com.example.data.auth.repository.AuthRepository

abstract class SplashViewModel : BaseViewModel(){

    abstract val isAuthenticatedLiveData: LiveData<Boolean>

    abstract fun checkAuthentication()

}

class SplashViewModelImpl(private val authRepository: AuthRepository) : SplashViewModel() {

    override val isAuthenticatedLiveData: MutableLiveData<Boolean> = MutableLiveData()

    override fun checkAuthentication() {
        val isTokenPresent = authRepository.getToken()
        isAuthenticatedLiveData.postValue(isTokenPresent.isNotEmpty())
    }

}