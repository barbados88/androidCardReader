package com.abank.IDCard.presentation.activity.scan

import android.content.Intent
import android.os.Bundle
import com.abank.IDCard.R
import com.abank.IDCard.presentation.base.BaseActivity
import com.abank.IDCard.presentation.fragment.IntentData
import com.abank.IDCard.utils.Extensions.myStartActivityForResult
import kotlinx.android.synthetic.main.activity_container.*
import net.sf.scuba.data.Gender
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult
import org.jmrtd.lds.icao.MRZInfo

class ContainerActivity: BaseActivity() {

    companion object {

        const val REQUEST_MRZ = 12
        const val REQUEST_NFC = 11
        const val RECOGNIZED_TEXT = "recognized_text"
    }

    private var mrzInfo: MRZInfo = MRZInfo("P",
            "ESP",
            "DUMMY",
            "DUMMY",
            "number",
            "ESP",
            "birthday",
            Gender.MALE,
            "expiration",
            "DUMMY"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_container)
        addClickListeners()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode) {
            REQUEST_MRZ -> checkData(data)
        }
    }

    // MARK: - Actions

    // MARK: - Helper methods

    private fun addClickListeners() {
        automaticButton.setOnClickListener {
            myStartActivityForResult<ScannerActivity>(REQUEST_MRZ)
        }
        buttonReadNFC.setOnClickListener {
            onPassportRead()
        }
    }

    private fun checkData(intent: Intent?) {
        if (intent == null || intent.extras == null) return
        val text = intent.getStringExtra(RECOGNIZED_TEXT)
        val strings = text!!.split("\n")
        val first = strings[0].replace("[A-Z]".toRegex(), "")
        val second = strings[1].replace("[A-Z]".toRegex(), "")
        documentInputLayout.editText?.setText(first.substring(0, 9))
        dateInputLayout.editText?.setText(second.substring(0, 6))
        expireInputLayout.editText?.setText(second.subSequence(7, 13))
    }

    private fun onPassportRead() {
        mrzInfo.documentNumber = documentInputLayout.editText?.text.toString()
        mrzInfo.dateOfBirth = dateInputLayout.editText?.text.toString()
        mrzInfo.dateOfExpiry = expireInputLayout.editText?.text.toString()
        val intent = Intent(this, NFCReaderActivity::class.java)
        intent.putExtra(IntentData.KEY_MRZ_INFO, mrzInfo)
        startActivityForResult(intent, REQUEST_NFC)
    }

}