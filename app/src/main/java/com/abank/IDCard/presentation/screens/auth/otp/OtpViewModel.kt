package com.abank.idcard.presentation.screens.auth.otp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.abank.idcard.presentation.base.BaseViewModel
import com.example.data.auth.model.OTPRequest
import com.example.data.auth.repository.AuthRepository

abstract class OtpViewModel : BaseViewModel() {

    abstract val errorLiveData: LiveData<String>
    abstract val successLiveData: LiveData<String>

    abstract fun sendCode(token: String, code: String)

}

class OtpViewModelImpl(private val authRepository: AuthRepository) : OtpViewModel() {

    override val errorLiveData: MutableLiveData<String> = MutableLiveData()
    override val successLiveData: MutableLiveData<String> = MutableLiveData()

    override fun sendCode(token: String, code: String) {
        authRepository.checkOTP(OTPRequest(token, code)).execute(errorLiveData) {
            authRepository.storeToken(it.authToken ?: "")
            successLiveData.postValue(it.authToken)
        }
    }

}