package com.abank.idcard.utils.NFC.ABExtends

import org.jmrtd.lds.icao.DG11File
import org.jmrtd.lds.icao.DG12File
import org.jmrtd.lds.icao.DG1File
import java.io.InputStream

class ABLDSFileUtil {

    companion object {

        fun get1File(stream: InputStream): DG1File {
            return DG1File(stream)
        }

        fun get11File(stream: InputStream): DG11File {
            return DG11File(stream)
        }

        fun get12File(stream: InputStream): DG12File {
            return  DG12File(stream)
        }

        fun get32File(stream: InputStream): DG32File {
            return DG32File(stream)
        }

        fun get33File(stream: InputStream): DG33File {
            return DG33File(stream)
        }

        fun get34File(stream: InputStream): DG34File {
            return DG34File(stream)
        }

    }

}