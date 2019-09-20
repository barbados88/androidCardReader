package com.abank.IDCard.utils.WXAdapter.Holders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.abank.IDCard.utils.NFC.Passport
import kotlinx.android.synthetic.main.viewholder_passport_info.view.*

enum class PassportInfo {

    dateOfBirth, dateOfExpiry, documentCode, documentNumber, optionalData1, optionalData2, issuingState, primaryIdentifier, secondaryIdentifier, nationality, gender,
    custodyInformation, fullDateOfBirth, nameOfHolder, otherNames, otherValidTDNumbers, permanentAddress, personalNumber, personalSummary, placeOfBirth, profession, telephone,
    registrationInfo,
    marriageInfo;

    val title: String
    get() {
        when(this) {
            dateOfBirth -> return "dateOfBirth"
            dateOfExpiry -> return "dateOfExpiry"
            documentCode -> return "documentCode"
            documentNumber -> return "documentNumber"
            optionalData1 -> return "optionalData1"
            optionalData2 -> return "optionalData2"
            issuingState -> return "issuingState"
            primaryIdentifier -> return "primaryIdentifier"
            secondaryIdentifier -> return "secondaryIdentifier"
            nationality -> return "nationality"
            gender -> return "gender"

            custodyInformation -> return "custodyInformation"
            fullDateOfBirth -> return "fullDateOfBirth"
            nameOfHolder -> return "nameOfHolder"
            otherNames -> return "otherNames"
            otherValidTDNumbers -> return "otherValidTDNumbers"
            permanentAddress -> return "permanentAddress"
            personalNumber -> return "personalNumber"
            personalSummary -> return "personalSummary"
            placeOfBirth -> return "placeOfBirth"
            profession -> return "profession"
            telephone -> return "telephone"

            registrationInfo -> return "registrationInfo"

            marriageInfo -> return "marriageInfo"
        }
    }

    fun details(passport: Passport): String? {
        when(this) {
            dateOfBirth -> return passport.personDetails?.dateOfBirth
            dateOfExpiry -> return passport.personDetails?.dateOfExpiry
            documentCode -> return passport.personDetails?.documentCode
            documentNumber -> return passport.personDetails?.documentNumber
            optionalData1 -> return passport.personDetails?.optionalData1
            optionalData2 -> return passport.personDetails?.optionalData2
            issuingState -> return passport.personDetails?.issuingState
            primaryIdentifier -> return passport.personDetails?.primaryIdentifier
            secondaryIdentifier -> return passport.personDetails?.secondaryIdentifier
            nationality -> return passport.personDetails?.nationality
            gender -> return passport.personDetails?.gender.toString()

            custodyInformation -> return passport.additionalPersonDetails?.custodyInformation
            fullDateOfBirth -> return passport.additionalPersonDetails?.fullDateOfBirth
            nameOfHolder -> return passport.additionalPersonDetails?.nameOfHolder
            otherNames -> return passport.additionalPersonDetails?.otherNames.toString()
            otherValidTDNumbers -> return passport.additionalPersonDetails?.otherValidTDNumbers.toString()
            permanentAddress -> return passport.additionalPersonDetails?.permanentAddress.toString()
            personalNumber -> return passport.additionalPersonDetails?.personalNumber
            personalSummary -> return passport.additionalPersonDetails?.personalSummary
            placeOfBirth -> return passport.additionalPersonDetails?.placeOfBirth.toString()
            profession -> return passport.additionalPersonDetails?.profession
            telephone -> return passport.additionalPersonDetails?.telephone

            registrationInfo -> return passport.registrationInfo

            marriageInfo -> return passport.marriageInfo
        }
    }

}

class InfoViewHolder(containerView: View): RecyclerView.ViewHolder(containerView) {

    fun configure(type: PassportInfo, passport: Passport) {
        itemView.titleTextView.text = type.title
        itemView.detailsTextView.text = type.details(passport)
    }

}