package com.abank.IDCard.utils.NFC

/**
 * Parameters for PKD backed certificate store, selecting certificates provided
 * in CSCA master lists.
 *
 * @author The JMRTD team (info@jmrtd.org)
 *
 * @version $Revision: $
 */
class PKDMasterListCertStoreParameters : PKDCertStoreParameters {

    constructor() : super()

    @JvmOverloads
    constructor(serverName: String, baseDN: String = DEFAULT_BASE_DN) : super(serverName, baseDN)

    @JvmOverloads
    constructor(serverName: String, port: Int, baseDN: String = DEFAULT_BASE_DN) : super(serverName, port, baseDN)

    companion object {

        private val DEFAULT_BASE_DN = "dc=CSCAMasterList,dc=pkdDownload"
    }
}