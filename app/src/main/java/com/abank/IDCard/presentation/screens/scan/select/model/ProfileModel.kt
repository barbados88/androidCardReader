package com.abank.idcard.presentation.screens.scan.select.model

import com.example.data.scan.model.ProfileResponse

data class ProfileModel(
        val namesInfo: String?,
        val email: String?,
        val photo: String?
)

fun ProfileResponse.toProfile() =
        ProfileModel(
                namesInfo = makeNamesInfo(),
                email = email,
                photo = photoUrl
        )

private fun ProfileResponse.makeNamesInfo() =
        "${resolveRuUa(surnameRu ?: "", surnameRu ?: "")} " +
                "${resolveRuUa(nameRu ?: "", nameUa ?: "")} " +
                resolveRuUa(patronymicRu ?: "", patronymicUa ?: "")

private fun ProfileResponse.resolveRuUa(stringRu: String, stringUa: String) = if(language == "RUS") stringRu else stringUa