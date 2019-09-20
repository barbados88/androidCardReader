package com.abank.IDCard.utils.NFC

import android.content.Context
import android.nfc.Tag
import android.nfc.tech.IsoDep
import com.abank.IDCard.utils.NFC.ABExtends.ABLDSFileUtil
import com.abank.IDCard.utils.NFC.ABExtends.ABPassportService
import com.abank.IDCard.utils.NFC.ABExtends.DG32File
import com.abank.IDCard.utils.NFC.ABExtends.DG33File
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import net.sf.scuba.smartcards.CardService
import net.sf.scuba.smartcards.CardServiceException
import org.jmrtd.*
import org.jmrtd.lds.CardAccessFile
import org.jmrtd.lds.icao.DG11File
import org.jmrtd.lds.icao.MRZInfo
import java.io.InputStream

class NFCDocumentTag {

    fun readTags(context: Context?, tag: Tag, mrzInfo: MRZInfo, passportCallback: PassportCallback): Disposable {
        return Single.fromCallable {
            var ps: PassportService? = null
            var cardServiceException: Exception? = null
            val passport = Passport()
            try {
                val nfc = IsoDep.get(tag)
                val cs = CardService.getInstance(nfc)
                ps = ABPassportService(cs, PassportService.NORMAL_MAX_TRANCEIVE_LENGTH, PassportService.DEFAULT_MAX_BLOCKSIZE, false, true)
                ps.open()
                CardAccessFile(ps.getInputStream(PassportService.EF_CARD_ACCESS))
                ps.sendSelectApplet(false)
                val bacKey = BACKey(mrzInfo?.documentNumber, mrzInfo?.dateOfBirth, mrzInfo?.dateOfExpiry)
                ps.doBAC(bacKey)
                var is1: InputStream? = null
                var is11: InputStream? = null
                var is32: InputStream? = null
                try {
                    is1 = ps.getInputStream(PassportService.EF_DG1)
                    saveDG1To(passport, ABLDSFileUtil.get1File(is1).mrzInfo)
                    is11 = ps.getInputStream(PassportService.EF_DG11)
                    saveDG11To(passport, ABLDSFileUtil.get11File(is11))
                    is32 = ps.getInputStream(ABPassportService.EF_DG32)
                    saveDG32To(passport, ABLDSFileUtil.get32File(is32))
                } catch (ex: Exception) {
                    cardServiceException = ex
                    print(ex.localizedMessage)
                    is1?.close()
                    is11?.close()
                    is32?.close()
                }
            } catch (ex: Exception) {
                cardServiceException = ex
                print(ex.localizedMessage)
                ps?.close()
            } finally {
                ps?.close()
            }
            PassportDTO(passport, cardServiceException)
        }
                .doOnSubscribe{
                    passportCallback.onPassportReadStart()
                }
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe{ passportDTO ->
                    if(passportDTO.cardServiceException!=null) {
                        val cardServiceException = passportDTO.cardServiceException
                        if (cardServiceException is AccessDeniedException) {
                            passportCallback.onAccessDeniedException(cardServiceException)
                        } else if (cardServiceException is BACDeniedException) {
                            passportCallback.onBACDeniedException(cardServiceException)
                        } else if (cardServiceException is PACEException) {
                            passportCallback.onPACEException(cardServiceException)
                        } else if (cardServiceException is CardServiceException) {
                            passportCallback.onCardException(cardServiceException)
                        } else {
                            passportCallback.onGeneralException(cardServiceException)
                        }
                    } else {
                        passportCallback.onPassportRead(passportDTO.passport)
                    }
                    passportCallback.onPassportReadFinish()
                }
    }

    private fun saveDG1To(passport: Passport, mrzInfo: MRZInfo) {
        val personDetails = PersonDetails()
        personDetails.dateOfBirth = mrzInfo.dateOfBirth
        personDetails.dateOfExpiry = mrzInfo.dateOfExpiry
        personDetails.documentCode = mrzInfo.documentCode
        personDetails.documentNumber = mrzInfo.documentNumber
        personDetails.optionalData1 = mrzInfo.optionalData1
        personDetails.optionalData2 = mrzInfo.optionalData2
        personDetails.issuingState = mrzInfo.issuingState
        personDetails.primaryIdentifier = mrzInfo.primaryIdentifier
        personDetails.secondaryIdentifier = mrzInfo.secondaryIdentifier
        personDetails.nationality = mrzInfo.nationality
        personDetails.gender = mrzInfo.gender

        passport.personDetails = personDetails
    }

    private fun saveDG11To(passport: Passport, dg11: DG11File) {
        val additionalPersonDetails = AdditionalPersonDetails()
        additionalPersonDetails.custodyInformation = dg11.custodyInformation
        additionalPersonDetails.fullDateOfBirth = dg11.fullDateOfBirth
        additionalPersonDetails.nameOfHolder = dg11.nameOfHolder
        additionalPersonDetails.otherNames = dg11.otherNames
        additionalPersonDetails.otherValidTDNumbers = dg11.otherValidTDNumbers
        additionalPersonDetails.permanentAddress = dg11.permanentAddress
        additionalPersonDetails.personalNumber = dg11.personalNumber
        additionalPersonDetails.personalSummary = dg11.personalSummary
        additionalPersonDetails.placeOfBirth = dg11.placeOfBirth
        additionalPersonDetails.profession = dg11.profession
        additionalPersonDetails.proofOfCitizenship = dg11.proofOfCitizenship
        additionalPersonDetails.tagPresenceList = dg11.tagPresenceList
        additionalPersonDetails.telephone = dg11.telephone

        passport.additionalPersonDetails = additionalPersonDetails
    }

    private fun saveDG32To(passport: Passport, dg32: DG32File) {
        passport.registrationInfo = dg32.getregistrationInfo()
    }

    data class PassportDTO(val passport: Passport? = null, val cardServiceException: Exception? = null)

    interface PassportCallback {
        fun onPassportReadStart()
        fun onPassportReadFinish()
        fun onPassportRead(passport: Passport?)
        fun onAccessDeniedException(exception: AccessDeniedException)
        fun onBACDeniedException(exception: BACDeniedException)
        fun onPACEException(exception: PACEException)
        fun onCardException(exception: CardServiceException)
        fun onGeneralException(exception: Exception?)
    }

}