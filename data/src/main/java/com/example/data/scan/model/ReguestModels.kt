package com.example.data.scan.model

import com.google.gson.annotations.SerializedName

data class DataRequest(@SerializedName("order_id") val orderId: String,
                       @SerializedName("idcard_num") val cardNumber: String,
                       @SerializedName("sex") val sex: String,
                       @SerializedName("citizenship") val citizenship: String,
                       @SerializedName("track") val track: String,
                       @SerializedName("name_surname") val name: String,
                       @SerializedName("patronymic") val patronymic: String,
                       @SerializedName("registry_entry_number") val registryNumber: String,
                       @SerializedName("client_birth_place") val birthPlace: String,
                       @SerializedName("client_birth_date") val birthDate: String,
                       @SerializedName("document_authority") val docAuthority: String,
                       @SerializedName("document_date") val docDate: String,
                       @SerializedName("document_date_end") val docEndDate: String,
                       @SerializedName("registration_place") val regPlace: String,
                       @SerializedName("registration_place_date") val regDate: String,
                       @SerializedName("marital_status") val marriage: String,
                       @SerializedName("marital_status_date") val marriageDate: String,
                       @SerializedName("itn") val itn: String)