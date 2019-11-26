package com.abank.idcard.utils.NFC.ABExtends

import net.sf.scuba.tlv.TLVInputStream
import org.jmrtd.lds.DataGroup
import java.io.DataInputStream
import java.io.InputStream
import java.io.OutputStream

const val EF_DG32_TAG = 0x7F20

class DG32File(inputStream: InputStream): DataGroup(EF_DG32_TAG, inputStream) {

    private var registration: String? = null
    private var regDate: String? = null

    fun getRegistrationInfo(): String {
        return registration ?: "Нет данных"
    }

    fun getRegistrationDate(): String {
        return regDate ?: "Нет данных"
    }

    companion object {
        const val REGISTER_TAG = 0x04
        const val REG_DATE_TAG = 0x5F26
    }

    override fun getTag(): Int {
        return EF_DG32_TAG
    }

    override fun toString(): String {
        return "DG32File " + registration.toString().replace("\n".toRegex(), "").trim()
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
        tlvInputStream.skipToTag(REGISTER_TAG)
        var length = tlvInputStream.readLength()
        registration = readObject(tlvInputStream, length)
        tlvInputStream.skipToTag(REG_DATE_TAG)
        length = tlvInputStream.readLength()
        regDate = readObject(tlvInputStream, length)
    }

    // MARK: - Helper methods

    private fun readObject(stream: InputStream, length: Int): String {
        val dataIn = DataInputStream(stream)
        val data = ByteArray(length)
        dataIn.readFully(data)
        return String(data).trim()
    }

}