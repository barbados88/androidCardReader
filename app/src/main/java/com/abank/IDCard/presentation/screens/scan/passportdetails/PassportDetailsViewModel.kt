package com.abank.idcard.presentation.screens.scan.passportdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.abank.idcard.presentation.base.BaseViewModel
import com.abank.idcard.utils.NFC.Passport
import com.example.data.scan.model.DataRequest
import com.example.data.scan.repository.ScanRepository

abstract class PassportDetailsViewModel : BaseViewModel() {

    abstract val errorLiveData: LiveData<String>
    abstract val successLiveData: LiveData<Unit>

    abstract fun sendData(passport: Passport)
}

class PassportDetailsViewModelImpl(private val scanRepository: ScanRepository) : PassportDetailsViewModel() {

    override val errorLiveData: MutableLiveData<String> = MutableLiveData()
    override val successLiveData: MutableLiveData<Unit> = MutableLiveData()

    override fun sendData(passport: Passport) {
        scanRepository.sendData(passport.toDataRequest()).execute(errorLiveData) {
            successLiveData.postValue(Unit)
        }
    }

    private fun Passport.toDataRequest() =
            DataRequest(
                    orderId = orderId ?: "",
                    cardNumber = personDetails?.documentNumber ?: "",
                    sex = personDetails?.gender?.name?.get(0).toString(),
                    citizenship = personDetails?.nationality ?: "",
                    track = track ?: "",
                    name = additionalPersonDetails?.nameOfHolder ?: "",
                    patronymic = additionalPersonDetails?.otherNames?.joinToString(" ") ?: "",
                    registryNumber = additionalPersonDetails?.personalNumber ?: "",
                    birthPlace = additionalPersonDetails?.placeOfBirth?.joinToString("<") ?: "",
                    birthDate = additionalPersonDetails?.fullDateOfBirth ?: "",
                    docAuthority = issueAuthority ?: "",
                    docDate = documentDate ?: "",
                    docEndDate = personDetails?.dateOfExpiry ?: "",
                    regPlace = registrationInfo ?: "",
                    regDate = registrationDate ?: "",
                    marriage = marriageInfo ?: "",
                    marriageDate = marriageDate ?: "",
                    itn = innInfo ?: "")

}