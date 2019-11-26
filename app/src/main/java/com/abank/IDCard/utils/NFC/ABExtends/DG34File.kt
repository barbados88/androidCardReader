package com.abank.idcard.utils.NFC.ABExtends

import net.sf.scuba.tlv.TLVInputStream
import org.jmrtd.lds.DataGroup
import java.io.*

const val EF_DG34_TAG = 0x7F22

class DG34File(inputStream: InputStream): DataGroup(EF_DG34_TAG, inputStream) {

    private var innInfo: String? = null

    fun getInnInfo(): String {
        return innInfo ?: "Нет данных"
    }

    companion object {
        const val INN_TAG = 0x30
    }

    override fun getTag(): Int {
        return EF_DG34_TAG
    }

    override fun toString(): String {
       return "DG34File " + innInfo.toString().replace("\n".toRegex(), "").trim()
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
        val tag = tlvInputStream.readTag()
        if (tag == INN_TAG) {
            tlvInputStream.readLength()
            tlvInputStream.readTag()
            val length = tlvInputStream.readLength()
            readObject(tlvInputStream, length)
        }
    }

    // MARK: - Helper methods

    private fun readObject(stream: InputStream, length: Int) {
        val dataIn = DataInputStream(stream)
        val data = ByteArray(length)
        dataIn.readFully(data)
        innInfo = String(data).trim()
    }

}