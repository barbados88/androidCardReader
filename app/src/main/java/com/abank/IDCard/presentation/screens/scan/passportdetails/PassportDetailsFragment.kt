package com.abank.idcard.presentation.screens.scan.passportdetails

import android.os.Bundle
import android.view.View
import com.abank.idcard.R
import com.abank.idcard.presentation.base.BaseFragment
import com.abank.idcard.presentation.fragment.IntentData
import com.abank.idcard.utils.Extensions.bindDataTo
import com.abank.idcard.utils.Extensions.visible
import com.abank.idcard.utils.NFC.Passport
import kotlinx.android.synthetic.main.fragment_passport_details.*
import org.jetbrains.anko.bundleOf
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.viewModel

class PassportDetailsFragment : BaseFragment() {

    override val navigator: PassportDetailsNavigator = get()

    override fun getLayout() = R.layout.fragment_passport_details

    private val passport: Passport?
        get() = arguments?.getParcelable(IntentData.KEY_PASSPORT)

    private val viewModel: PassportDetailsViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sendData()
        subscribeLiveData()
    }

    private fun sendData() {
        if (passport == null) return
        passport?.let { viewModel.sendData(it) }
    }

    private fun subscribeLiveData() {
        bindDataTo(viewModel.errorLiveData) { showSnack(it) }
        bindDataTo(viewModel.successLiveData) {
            successLayout.visible()
            buttonBack.setOnClickListener { navigator.goSelect() }
        }

    }

    companion object {

        fun newInstance(passport: Passport) =
                PassportDetailsFragment().apply {
                    arguments = bundleOf(IntentData.KEY_PASSPORT to passport)
                }

    }

}