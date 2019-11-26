package com.abank.idcard.data.repository.server.pojo.request


import com.example.data.BuildConfig.API_KEY
import com.google.gson.annotations.SerializedName

data class LoginRequest(@SerializedName("phone") var login: String,
                        @SerializedName("password") var password: String,
                        @SerializedName("request_ref")var ref: String = API_KEY)

data class OTPRequest(@SerializedName("token") var token: String,
                      @SerializedName("password") var otp: String)

data class LogoutRequest(@SerializedName("token") var token: String)

data class DataRequest(@SerializedName("order_id") var orderId: String,
                       @SerializedName("idcard_num") var cardNumber: String,
                       @SerializedName("sex") var sex: String,
                       @SerializedName("citizenship") var citizenship: String,
                       @SerializedName("track") var track: String,
                       @SerializedName("name_surname") var name: String,
                       @SerializedName("patronymic") var patronymic: String,
                       @SerializedName("registry_entry_number") var registryNumber: String,
                       @SerializedName("client_birth_place") var birthPlace: String,
                       @SerializedName("client_birth_date") var birthDate: String,
                       @SerializedName("document_authority") var docAuthority: String,
                       @SerializedName("document_date") var docDate: String,
                       @SerializedName("document_date_end") var docEndDate: String,
                       @SerializedName("registration_place") var regPlace: String,
                       @SerializedName("registration_place_date") var regDate: String,
                       @SerializedName("marital_status") var marriage: String,
                       @SerializedName("marital_status_date") var marriageDate: String,
                       @SerializedName("itn") var itn: String)