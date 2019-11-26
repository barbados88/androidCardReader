package com.abank.idcard.data.repository.repository

import com.abank.idcard.data.repository.database.DataBase
import com.abank.idcard.data.repository.server.ServerCommunicator
import com.abank.idcard.data.repository.server.pojo.request.DataRequest
import com.abank.idcard.data.repository.server.pojo.request.LoginRequest
import com.abank.idcard.data.repository.server.pojo.request.LogoutRequest
import com.abank.idcard.data.repository.server.pojo.request.OTPRequest
import com.abank.idcard.data.repository.server.pojo.response.LoginResponse
import com.abank.idcard.data.repository.server.pojo.response.TransactionsResponse
import com.abank.idcard.utils.NFC.Passport
import io.reactivex.Single

class ReaderRepository(private val communicator: ServerCommunicator,
                       private val dataBase: DataBase) {


    fun authUser(body: LoginRequest): Single<LoginResponse> {
        return communicator.auth(body)
    }

    fun checkOTP(body: OTPRequest): Single<LoginResponse> {
        return communicator.checkOTP(body)
    }

    fun logout(body: LogoutRequest): Single<LoginResponse> {
        return communicator.logout(body)
    }

    fun getOrders(): Single<TransactionsResponse> {
        return communicator.getOrders()
    }

    fun sendData(passport: Passport): Single<LoginResponse> {
        return  communicator.sendData(
                DataRequest(
                        orderId = passport.orderId ?: "",
                        cardNumber = passport.personDetails?.documentNumber ?: "",
                        sex = passport.personDetails?.gender?.name?.get(0).toString(),
                        citizenship = passport.personDetails?.nationality ?: "",
                        track = passport.track ?: "",
                        name = passport.additionalPersonDetails?.nameOfHolder ?: "",
                        patronymic = passport.additionalPersonDetails?.otherNames?.joinToString(" ") ?: "",
                        registryNumber = passport.additionalPersonDetails?.personalNumber ?: "",
                        birthPlace = passport.additionalPersonDetails?.placeOfBirth?.joinToString("<") ?: "",
                        birthDate = passport.additionalPersonDetails?.fullDateOfBirth ?: "",
                        docAuthority = passport.issueAuthority ?: "",
                        docDate = passport.documentDate ?: "",
                        docEndDate = passport.personDetails?.dateOfExpiry ?: "",
                        regPlace = passport.registrationInfo ?: "",
                        regDate = passport.registrationDate ?: "",
                        marriage = passport.marriageInfo ?: "",
                        marriageDate = passport.marriageDate ?: "",
                        itn = passport.innInfo ?: ""))
    }

}