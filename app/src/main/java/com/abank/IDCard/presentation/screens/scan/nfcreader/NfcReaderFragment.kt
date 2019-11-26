package com.abank.idcard.presentation.screens.scan.nfcreader

import android.content.Intent
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.abank.idcard.R
import com.abank.idcard.presentation.base.BaseFragment
import com.abank.idcard.utils.NFC.NFCDocumentTag
import com.abank.idcard.utils.NFC.Passport
import io.reactivex.disposables.CompositeDisposable
import net.sf.scuba.smartcards.CardServiceException
import net.sf.scuba.smartcards.ISO7816
import org.jmrtd.AccessDeniedException
import org.jmrtd.BACDeniedException
import org.jmrtd.PACEException
import org.jmrtd.lds.icao.MRZInfo
import org.koin.android.ext.android.get
import org.spongycastle.jce.provider.BouncyCastleProvider
import java.security.Security

class NfcReaderFragment : BaseFragment() {

    override val navigator: NfcReaderNavigator = get()

    override fun getLayout() = R.layout.fragment_nfc

    private var mrzInfo: MRZInfo? = null
    private var nfcFragmentListener: NfcFragmentListener? = null
    private var textViewPassportNumber: TextView? = null
    private var textViewDateOfBirth: TextView? = null
    private var textViewDateOfExpiry: TextView? = null
    private var progressBar: ProgressBar? = null

    private var mHandler = Handler(Looper.getMainLooper())
    var disposable = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val inflatedView = inflater.inflate(R.layout.fragment_nfc, container, false)

        return inflatedView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textViewPassportNumber = view.findViewById(R.id.value_passport_number)
        textViewDateOfBirth = view.findViewById(R.id.value_DOB)
        textViewDateOfExpiry = view.findViewById(R.id.value_expiration_date)
        progressBar = view.findViewById(R.id.progressBar)
        nfcFragmentListener = activity as NFCReaderActivity
    }

    fun startParseTag(intent: Intent) {
        if (intent.extras == null) return
        val tag = intent.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG) ?: return
        val subscribe = NFCDocumentTag().readTags(context, tag, mrzInfo!!, object : NFCDocumentTag.PassportCallback {
            override fun onPassportReadStart() {
                onNFCSReadStart()
            }

            override fun onPassportReadFinish() {
                onNFCReadFinish()
            }

            override fun onPassportRead(passport: Passport?) {
                this@NfcReaderFragment.onPassportRead(passport)
            }

            override fun onAccessDeniedException(exception: AccessDeniedException) {
                Toast.makeText(context, getString(R.string.warning_authentication_failed), Toast.LENGTH_SHORT).show()
                exception.printStackTrace()
                this@NfcReaderFragment.onCardException(exception)

            }

            override fun onBACDeniedException(exception: BACDeniedException) {
                Toast.makeText(context, exception.toString(), Toast.LENGTH_SHORT).show()
                this@NfcReaderFragment.onCardException(exception)
            }

            override fun onPACEException(exception: PACEException) {
                Toast.makeText(context, exception.toString(), Toast.LENGTH_SHORT).show()
                this@NfcReaderFragment.onCardException(exception)
            }

            override fun onCardException(exception: CardServiceException) {
                val sw = exception.sw.toShort()
                when (sw) {
                    ISO7816.SW_CLA_NOT_SUPPORTED -> {
                        Toast.makeText(context, getString(R.string.warning_cla_not_supported), Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        Toast.makeText(context, exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
                this@NfcReaderFragment.onCardException(exception)
            }

            override fun onGeneralException(exception: Exception?) {
                Toast.makeText(context, exception!!.toString(), Toast.LENGTH_SHORT).show()
                this@NfcReaderFragment.onCardException(exception)
            }
        })
        disposable.add(subscribe)
    }

    override fun onDetach() {
        nfcFragmentListener = null
        super.onDetach()
    }


    override fun onResume() {
        super.onResume()

        textViewPassportNumber!!.text = getString(R.string.doc_number, mrzInfo!!.documentNumber)
        textViewDateOfBirth!!.text = getString(R.string.doc_dob, mrzInfo!!.dateOfBirth)
        textViewDateOfExpiry!!.text = getString(R.string.doc_expiry, mrzInfo!!.dateOfExpiry)

        if (nfcFragmentListener != null) {
            nfcFragmentListener!!.onEnableNfc()
        }
    }

    override fun onPause() {
        super.onPause()
        if (nfcFragmentListener != null) {
            nfcFragmentListener!!.onDisableNfc()
        }
    }

    override fun onDestroyView() {
        if (!disposable.isDisposed()) {
            disposable.dispose()
        }
        super.onDestroyView()
    }

    private fun onNFCSReadStart() {
        Log.d(TAG, "onNFCSReadStart")
        mHandler.post { progressBar!!.visibility = View.VISIBLE }

    }

    private fun onNFCReadFinish() {
        Log.d(TAG, "onNFCReadFinish")
        mHandler.post { progressBar!!.visibility = View.GONE }
    }

    private fun onCardException(cardException: Exception?) {
        mHandler.post {
            if (nfcFragmentListener != null) {
                nfcFragmentListener!!.onCardException(cardException)
            }
        }
    }

    protected fun onPassportRead(passport: Passport?) {
        mHandler.post {
            if (nfcFragmentListener != null) {
                nfcFragmentListener!!.onPassportRead(passport)
            }
        }
    }

    interface NfcFragmentListener {
        fun onEnableNfc()
        fun onDisableNfc()
        fun onPassportRead(passport: Passport?)
        fun onCardException(cardException: Exception?)
    }

    companion object {
        private val TAG = NfcReaderFragment::class.java.simpleName

        init {
            Security.addProvider(BouncyCastleProvider())
        }

        fun newInstance(mrzInfo: MRZInfo): NfcReaderFragment {
            val myFragment = NfcReaderFragment()
            myFragment.mrzInfo = mrzInfo
            return myFragment
        }
    }

    object IntentData {

        val KEY_MRZ_INFO = "KEY_MRZ_INFO"
        val KEY_PASSPORT = "KEY_PASSPORT"
        val KEY_IMAGE = "KEY_IMAGE"
        val OTP_TOKEN = "OTP_TOKEN"
        val ORDER_ID = "ORDER_ID"
        val TRACK = "TRACK"

    }
}
