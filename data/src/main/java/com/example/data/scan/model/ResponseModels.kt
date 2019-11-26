package com.example.data.scan.model

import com.example.data.auth.model.ReaderError
import com.google.gson.annotations.SerializedName

data class TransactionSingle(@SerializedName("title") val title: String?,
                             @SerializedName("id") val id: String?)

data class TransactionsResponse(@SerializedName("order_list") val transactions: List<TransactionSingle>,
                                @SerializedName("request_ref") val requestRef: String?,
                                @SerializedName("response_ref") val responseRef: String?,
                                @SerializedName("timestamp") val timestamp: String?,
                                @SerializedName("result") val result: String,
                                @SerializedName("error") val error: ReaderError? = null)

data class ProfileResponse(
        @SerializedName("name_ru")
        val nameRu: String?,
        @SerializedName("surname_ru")
        val surnameRu: String?,
        @SerializedName("patronymic_ru")
        val patronymicRu: String?,
        @SerializedName("name_ua")
        val nameUa: String?,
        @SerializedName("surname_ua")
        val surnameUa: String?,
        @SerializedName("patronymic_ua")
        val patronymicUa: String?,
        @SerializedName("photo")
        val photoUrl: String?,
        @SerializedName("email")
        val email: String?,
        @SerializedName("language")
        val language: String?,
        @SerializedName("error")
        val error: ReaderError? = null
)