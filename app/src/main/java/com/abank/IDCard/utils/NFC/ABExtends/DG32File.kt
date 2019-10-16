package com.abank.IDCard.utils.NFC.ABExtends

import net.sf.scuba.tlv.TLVInputStream
import net.sf.scuba.tlv.TLVUtil
import okio.Utf8
import org.jmrtd.lds.DataGroup
import java.io.ByteArrayInputStream
import java.io.DataInputStream
import java.io.InputStream
import java.io.OutputStream
import java.nio.charset.Charset

const val EF_DG32_TAG = 0x7F20

class DG32File(inputStream: InputStream): DataGroup(EF_DG32_TAG, inputStream) {

    private var registration: String? = null

    fun getRegistrationInfo(): String {
        return registration ?: "Нет данных"
    }

    companion object {
        const val REGISTER_TAG = 0x04

    }

    override fun getTag(): Int {
        return EF_DG32_TAG
    }

    override fun toString(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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
        val length = tlvInputStream.readLength()
        readObject(tlvInputStream, length)
    }

    // MARK: - Helper methods

    private fun readObject(stream: InputStream, length: Int) {
        val dataIn = DataInputStream(stream)
        val data = ByteArray(length)
        dataIn.readFully(data)
        registration = String(data).trim()
    }

}