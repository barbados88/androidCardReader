package com.abank.idcard.utils.NFC

import android.graphics.Bitmap
import android.os.Parcel
import android.os.Parcelable
import java.util.ArrayList

class Passport : Parcelable {

    var face: Bitmap? = null
    var portrait: Bitmap? = null
    var signature: Bitmap? = null
    var fingerprints: List<Bitmap>? = null
    var personDetails: PersonDetails? = null
    var additionalPersonDetails: AdditionalPersonDetails? = null
    var additionalDocumentDetails: AdditionalDocumentDetails? = null
    var registrationInfo: String? = null
    var registrationDate: String? = null
    var marriageInfo: String? = null
    var marriageDate: String? = null
    var innInfo: String? = null
    var documentDate: String? = null
    var issueAuthority: String? = null
    var orderId: String? = null
    var track: String? = null

    constructor(`in`: Parcel) {
        fingerprints = ArrayList()
        this.face = if (`in`.readInt() == 1) `in`.readParcelable(Bitmap::class.java.classLoader) else null
        this.portrait = if (`in`.readInt() == 1) `in`.readParcelable(Bitmap::class.java.classLoader) else null
        this.personDetails = if (`in`.readInt() == 1) `in`.readParcelable(PersonDetails::class.java.classLoader) else null
        this.additionalPersonDetails = if (`in`.readInt() == 1) `in`.readParcelable(AdditionalPersonDetails::class.java.classLoader) else null

        if (`in`.readInt() == 1) {
            `in`.readList(fingerprints, Bitmap::class.java.classLoader)
        }

        this.signature = if (`in`.readInt() == 1) `in`.readParcelable(Bitmap::class.java.classLoader) else null
        this.additionalDocumentDetails = if (`in`.readInt() == 1) `in`.readParcelable(AdditionalDocumentDetails::class.java.classLoader) else null
    }

    constructor() {
        fingerprints = ArrayList()
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(if (face != null) 1 else 0)
        if (face != null) {
            dest.writeParcelable(face, flags)
        }

        dest.writeInt(if (portrait != null) 1 else 0)
        if (portrait != null) {
            dest.writeParcelable(portrait, flags)
        }

        dest.writeInt(if (personDetails != null) 1 else 0)
        if (personDetails != null) {
            dest.writeParcelable(personDetails, flags)
        }

        dest.writeInt(if (additionalPersonDetails != null) 1 else 0)
        if (additionalPersonDetails != null) {
            dest.writeParcelable(additionalPersonDetails, flags)
        }

        dest.writeInt(if (fingerprints != null) 1 else 0)
        if (fingerprints != null) {
            dest.writeList(fingerprints)
        }

        dest.writeInt(if (signature != null) 1 else 0)
        if (signature != null) {
            dest.writeParcelable(signature, flags)
        }

        dest.writeInt(if (additionalDocumentDetails != null) 1 else 0)
        if (additionalDocumentDetails != null) {
            dest.writeParcelable(additionalDocumentDetails, flags)
        }

    }

    companion object {

        @JvmField
        val CREATOR: Parcelable.Creator<*> = object : Parcelable.Creator<Passport> {
            override fun createFromParcel(pc: Parcel): Passport {
                return Passport(pc)
            }

            override fun newArray(size: Int): Array<Passport?> {
                return arrayOfNulls(size)
            }
        }
    }

}