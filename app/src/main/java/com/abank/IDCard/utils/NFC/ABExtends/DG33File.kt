package com.abank.idcard.utils.NFC.ABExtends

import net.sf.scuba.tlv.TLVInputStream
import org.jmrtd.lds.DataGroup
import java.io.DataInputStream
import java.io.InputStream
import java.io.OutputStream

const val EF_DG33_TAG = 0x7F21

class DG33File(inputStream: InputStream): DataGroup(EF_DG33_TAG, inputStream) {

    private var marriageInfo: String? = null
    private var marDate: String? = null

    fun getMarriageInfo(): String {
        return marriageInfo ?: "Нет данных"
    }

    fun getMarriageDate(): String {
        return marDate ?: "Нет данных"
    }

    companion object {
        const val MARRIAGE_TAG = 0x04
        const val REG_DATE_TAG = 0x5F26
    }

    override fun getTag(): Int {
        return EF_DG33_TAG
    }

    override fun toString(): String {
        return "DG33File " + marriageInfo.toString().replace("\n".toRegex(), "").trim()
    }

    override fun equals(other: Any?): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hashCode(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun writeContent(outputStream: OutputStream?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    // MARK: - need to be override

    override fun readContent(inputStream: InputStream?) {
        val tlvInputStream = if (inputStream is TLVInputStream) inputStream else TLVInputStream(inputStream)
        tlvInputStream.skipToTag(MARRIAGE_TAG)
        var length = tlvInputStream.readLength()
        marriageInfo = readObject(tlvInputStream, length)
        tlvInputStream.skipToTag(REG_DATE_TAG)
        length = tlvInputStream.readLength()
        marDate = readObject(tlvInputStream, length)
    }

    // MARK: - Helper methods

    private fun readObject(stream: InputStream, length: Int): String {
        val dataIn = DataInputStream(stream)
        val data = ByteArray(length)
        dataIn.readFully(data)
        return String(data).trim()
    }

}