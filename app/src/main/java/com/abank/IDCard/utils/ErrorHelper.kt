package com.abank.idcard.utils

import android.content.Context
import org.json.JSONObject
import retrofit2.HttpException
import java.lang.IllegalStateException

object ErrorHelper {

    fun parseErrorAndGetString(context: Context, throwable: Throwable): String {
        return parseErrorAndGetString(context, getErrorCode(throwable))
    }

    fun parseErrorAndGetString(context: Context, errorCode: Int): String {
        var resultError = "Error"
        when (errorCode) {
            // switch code of error
        }
        return resultError
    }

    fun getErrorCode(throwable: Throwable): Int {
        var resultCode = -1
        try {
            val responseBody = (throwable as HttpException).response().errorBody()
            if (responseBody != null) {
                val jsonObject = JSONObject(responseBody.string())
                val errorObject = jsonObject.getJSONObject("error")
                resultCode = errorObject.optInt("code", -1)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return resultCode
    }

}

fun illegalState(why: String): Nothing = throw IllegalStateException(why)