package com.abank.idcard.presentation.screens.auth.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.abank.idcard.presentation.base.BaseViewModel
import com.example.data.auth.model.LoginRequest
import com.example.data.auth.repository.AuthRepository

abstract class SignInViewModel : BaseViewModel() {

    abstract val errorLiveData: LiveData<String>
    abstract val signedInLiveData: LiveData<String>

    abstract fun authUser(login: String, password: String)

}

class SignInViewModelImpl(private val authRepository: AuthRepository) : SignInViewModel() {

    override val errorLiveData: MutableLiveData<String> = MutableLiveData()
    override val signedInLiveData: MutableLiveData<String> = MutableLiveData()

    override fun authUser(login: String, password: String) {
        authRepository.authUser(LoginRequest(login, password)).execute(errorLiveData) { signedInLiveData.postValue(it.authToken) }
    }

}