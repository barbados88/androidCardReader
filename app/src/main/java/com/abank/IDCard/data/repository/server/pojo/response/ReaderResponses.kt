package com.abank.idcard.data.repository.server.pojo.response

import com.google.gson.JsonParser
import com.google.gson.annotations.SerializedName
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException

object ErrorHelper {

    fun parseErrorAndGetString(throwable: Throwable): String {
        val errorBody = (throwable as HttpException).response().errorBody()
        if (errorBody != null) {
            val objectError = JsonParser().parse(errorBody.string()).asJsonObject["error"]
            val msg = objectError.asJsonObject["title"]?.asString ?: objectError.asJsonObject["msg"]?.asString
            return msg ?: "Unknown format of server error"
        }
        return throwable.message.toString()
    }
}

data class ReaderError(@SerializedName("status") var status: String,
                       @SerializedName("msg") var msgError: String? = null,
                       @SerializedName("title") var titleError: String? = null): Throwable() {

    val errorMessage: String
    get() {
        return msgError ?: titleError ?: "Unknown format of server error"
    }

}

data class LoginResponse(@SerializedName("token") var authToken: String?,
                         @SerializedName("token_type") var tokenType: String?,
                         @SerializedName("expires_in") var expireDate: String?,
                         @SerializedName("otpSend") var isOtpSent: Boolean? = false,
                         @SerializedName("request_ref") var requestRef: String?,
                         @SerializedName("response_ref") var responseRef: String?,
                         @SerializedName("timestamp") var timestamp: String?,
                         @SerializedName("result") var result: String,
                         @SerializedName("error") var error: ReaderError? = null) {

    val token: String
    get() = authToken ?: ""

}

data class TransactionSingle(@SerializedName("title") var title: String?,
                             @SerializedName("id") var id: String?)

data class TransactionsResponse(@SerializedName("order_list") var transactions: List<TransactionSingle>,
                                @SerializedName("request_ref") var requestRef: String?,
                                @SerializedName("response_ref") var responseRef: String?,
                                @SerializedName("timestamp") var timestamp: String?,
                                @SerializedName("result") var result: String,
                                @SerializedName("error") var error: ReaderError? = null)