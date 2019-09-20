package com.abank.IDCard.utils.NFC.ABExtends

import net.sf.scuba.smartcards.CardService
import org.jmrtd.PassportService
import org.jmrtd.lds.LDSFile
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.OutputStream

class ABPassportService(service: CardService, tranceiveLength: Int, blockSize: Int, isSFIEnabled: Boolean, checkMAC: Boolean): PassportService(service, tranceiveLength, blockSize, isSFIEnabled, checkMAC) {

    companion object {
        val EF_DG32: Short = 0x0120
        val EF_DG33: Short = 0X0121
    }

}