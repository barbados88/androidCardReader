package com.abank.idcard.presentation.screens.scan.container

import android.os.Bundle
import android.view.View
import com.abank.idcard.R
import com.abank.idcard.presentation.base.BaseFragment
import com.abank.idcard.utils.Extensions.textObserver
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.fragment_container.*
import net.sf.scuba.data.Gender
import org.jetbrains.anko.bundleOf
import org.jmrtd.lds.icao.MRZInfo
import org.koin.android.ext.android.get

class ContainerFragment : BaseFragment() {

    override val navigator: ContainerNavigator = get()

    override fun getLayout() = R.layout.fragment_container

    private val inputs: ArrayList<TextInputLayout> = arrayListOf()

    private var track: String? = null

    private val orderId
        get() = arguments?.getString(ORDER_ID_KEY) ?: ""

    private val recognized
        get() = arguments?.getString(RECOGNIZED_TEXT)

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listenToTextChanges()
        addClickListeners()
        recognized?.let { checkData(it) }
    }

    private fun listenToTextChanges() {
        inputs.clear()
        inputs.addAll(arrayListOf(documentInputLayout, dateInputLayout, expireInputLayout))
        inputs.forEach { layout ->
            layout.editText?.textObserver()?.subscribe {
                layout.error = if (it.isEmpty()) getString(R.string.error_empty_text) else null
            }?.addtoBag()
        }
    }

    private fun checkData(text: String) {
        track = text
        val strings = text.split("\n")
        val first = strings[0].replace("[A-Z]".toRegex(), "")
        val second = strings[1].replace("[A-Z]".toRegex(), "")
        documentInputLayout.editText?.setText(first.substring(0, 9))
        dateInputLayout.editText?.setText(second.substring(0, 6))
        expireInputLayout.editText?.setText(second.subSequence(7, 13))
    }

    private fun addClickListeners() {
        automaticButton.setOnClickListener { navigator.toScanner() }
        buttonReadNFC.setOnClickListener { onPassportRead() }
    }

    private fun onPassportRead() {
        if (!allFieldsFilled()) return
        mrzInfo.documentNumber = documentInputLayout.editText?.text.toString()
        mrzInfo.dateOfBirth = dateInputLayout.editText?.text.toString()
        mrzInfo.dateOfExpiry = expireInputLayout.editText?.text.toString()
        navigator.toNfcReader(requireContext(), mrzInfo, orderId, track ?: "")
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

    companion object {

        private const val ORDER_ID_KEY = "order_id"

        const val RECOGNIZED_TEXT = "recognized"

        const val TAG = "CONTAINER"

        fun newInstance(orderId: String) = ContainerFragment().apply {
            arguments = bundleOf(ORDER_ID_KEY to orderId)
        }

    }

}