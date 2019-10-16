package com.abank.IDCard.presentation.activity.scan

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import com.abank.IDCard.R
import com.abank.IDCard.presentation.base.BaseActivity
import com.abank.IDCard.presentation.fragment.IntentData
import com.abank.IDCard.utils.Extensions.applyIoSchedulers
import com.abank.IDCard.utils.Extensions.myStartActivityForResult
import com.abank.IDCard.utils.Extensions.textObserver
import com.google.android.material.textfield.TextInputLayout
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

    private lateinit var inputs: Array<TextInputLayout>
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
        listenToTextChanges()
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

    private fun listenToTextChanges() {
        inputs = arrayOf(documentInputLayout, dateInputLayout, expireInputLayout)
        inputs.forEach {
            val layout = it
            val editText = it.editText
            editText?.let {
                editText.textObserver()
                        .applyIoSchedulers()
                        .subscribe {
                            layout.error = if (it.isEmpty()) getString(R.string.error_empty_text) else null
                        }
            }
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
        if (!allFieldsFilled()) return
        mrzInfo.documentNumber = documentInputLayout.editText?.text.toString()
        mrzInfo.dateOfBirth = dateInputLayout.editText?.text.toString()
        mrzInfo.dateOfExpiry = expireInputLayout.editText?.text.toString()
        val intent = Intent(this, NFCReaderActivity::class.java)
        intent.putExtra(IntentData.KEY_MRZ_INFO, mrzInfo)
        startActivityForResult(intent, REQUEST_NFC)
    }

    private fun allFieldsFilled(): Boolean {
        var allValid = true
        inputs.forEach {
            if (it.editText?.text.isNullOrEmpty()) {
                allValid = false
                it.error = getString(R.string.error_empty_text)
            }
        }
        return allValid
    }

}