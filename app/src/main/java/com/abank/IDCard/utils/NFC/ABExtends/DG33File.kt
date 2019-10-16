package com.abank.IDCard.utils.NFC.ABExtends

import net.sf.scuba.tlv.TLVInputStream
import org.jmrtd.lds.DataGroup
import java.io.DataInputStream
import java.io.InputStream
import java.io.OutputStream

const val EF_DG33_TAG = 0x7F21

class DG33File(inputStream: InputStream): DataGroup(EF_DG33_TAG, inputStream) {

    private var marriageInfo: String? = null

    fun getMarriageInfo(): String {
        return marriageInfo ?: "Нет данных"
    }

    companion object {
        const val MARRIAGE_TAG = 0x04
    }

    override fun getTag(): Int {
        return EF_DG33_TAG
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
        tlvInputStream.skipToTag(MARRIAGE_TAG)
        val length = tlvInputStream.readLength()
        readObject(tlvInputStream, length)
    }

    // MARK: - Helper methods

    private fun readObject(stream: InputStream, length: Int) {
        val dataIn = DataInputStream(stream)
        val data = ByteArray(length)
        dataIn.readFully(data)
        marriageInfo = String(data).trim()
    }

}