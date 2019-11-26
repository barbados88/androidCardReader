package com.abank.idcard.presentation.screens.scan.nfcreader

import android.app.PendingIntent
import android.content.Intent
import android.nfc.NfcAdapter
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import com.abank.idcard.R
import com.abank.idcard.presentation.base.BaseActivity
import com.abank.idcard.presentation.fragment.IntentData
import com.abank.idcard.presentation.fragment.PassportPhotoFragment
import com.abank.idcard.utils.Extensions.showSnack
import com.abank.idcard.utils.NFC.Passport
import org.jmrtd.lds.icao.MRZInfo
import org.koin.android.ext.android.get

class NFCReaderActivity : BaseActivity(), NfcReaderFragment.NfcFragmentListener, PassportPhotoFragment.PassportPhotoFragmentListener {

    private val navigator: NfcReaderNavigator = get()

    companion object {
        private val TAG_NFC = "TAG_NFC"
        private val TAG_PASSPORT_DETAILS = "TAG_PASSPORT_DETAILS"
        private val TAG_PASSPORT_PICTURE = "TAG_PASSPORT_PICTURE"
    }

    private var mrzInfo: MRZInfo? = null

    private var nfcAdapter: NfcAdapter? = null
    private var pendingIntent: PendingIntent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nfc)
        initNFC()
        navigator.attachFragmentManager(supportFragmentManager)
    }

    override fun onDestroy() {
        super.onDestroy()
        navigator.release()
    }

    override fun onResume() {
        super.onResume()
        nfcAdapter?.enableForegroundDispatch(this, pendingIntent, null, null)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        if (NfcAdapter.ACTION_TAG_DISCOVERED == intent.action || NfcAdapter.ACTION_TECH_DISCOVERED == intent.action) {
            processIntent(intent)
        }
    }

    private fun initNFC() {
        nfcAdapter = NfcAdapter.getDefaultAdapter(this)
        pendingIntent = PendingIntent.getActivity(this, 0,
                Intent(this, javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0)
        mrzInfo = intent.getSerializableExtra(IntentData.KEY_MRZ_INFO) as MRZInfo
        supportFragmentManager.beginTransaction()
                .replace(R.id.container, NfcReaderFragment.newInstance(mrzInfo!!), TAG_NFC)
                .commit()
    }

    private fun processIntent(checkIntent: Intent) {
        val fragmentByTag = supportFragmentManager.findFragmentByTag(TAG_NFC)
        if (fragmentByTag is NfcReaderFragment) {
            fragmentByTag.startParseTag(checkIntent)
        }
    }

    override fun onEnableNfc() {
        if (nfcAdapter != null) {
            if (!nfcAdapter!!.isEnabled) showWirelessSettings()

            nfcAdapter!!.enableForegroundDispatch(this, pendingIntent, null, null)
        }
    }

    override fun onDisableNfc() {
        val nfcAdapter = NfcAdapter.getDefaultAdapter(this)
        nfcAdapter.disableForegroundDispatch(this)
    }

    override fun onPassportRead(passport: Passport?) {
        if (passport?.personDetails == null) {
            showSnack(getString(R.string.error_reading_nfc))
            onBackPressed()
            return
        }
        showFragmentDetails(passport)
    }

    override fun onCardException(cardException: Exception?) {
        Toast.makeText(this, cardException.toString(), Toast.LENGTH_SHORT).show()
        //onBackPressed();
    }

    private fun showWirelessSettings() {
        Toast.makeText(this, getString(R.string.warning_enable_nfc), Toast.LENGTH_SHORT).show()
        val intent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
        startActivity(intent)
    }


    private fun showFragmentDetails(passport: Passport) {
        passport.track = intent.extras?.getString(IntentData.TRACK)
        passport.orderId = intent.extras?.getString(IntentData.ORDER_ID)
        navigator.goPassport(passport)
    }

}