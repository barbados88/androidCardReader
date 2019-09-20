package com.abank.IDCard.utils.NFC

import java.security.PrivateKey
import java.security.cert.Certificate

/**
 * Encapsulates the terminal key and associated certificte chain for terminal authentication.
 */
class EACCredentials
/**
 * Creates EAC credentials.
 *
 * @param privateKey
 * @param chain
 */
(val privateKey: PrivateKey, val chain: Array<Certificate>)