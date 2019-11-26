package com.abank.idcard.presentation.screens.auth.otp

import android.os.Bundle
import android.view.View
import com.abank.idcard.R
import com.abank.idcard.presentation.base.BaseFragment
import com.abank.idcard.utils.Extensions.bindDataTo
import com.abank.idcard.utils.Extensions.disable
import com.abank.idcard.utils.Extensions.enable
import com.abank.idcard.utils.Extensions.gone
import com.abank.idcard.utils.Extensions.visible
import kotlinx.android.synthetic.main.fragment_otp.*
import org.jetbrains.anko.bundleOf
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.viewModel

class OtpFragment : BaseFragment() {

    override val navigator: OtpNavigator = get()

    override fun getLayout() = R.layout.fragment_otp

    private val viewModel: OtpViewModel by viewModel()

    private val token
        get() = arguments?.getString(TOKEN_KEY) ?: ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPinBox()
        initClicks()
        subscribeLiveData()
    }

    private fun sendCode() {
        bSendOtp.disable()
        progress.visible()
        viewModel.sendCode(token, pbCode.text ?: "")
    }

    private fun initPinBox() {
        pbCode.onInputted = {
            progress.visible()
            bSendOtp.disable()
            viewModel.sendCode(token, it)
        }
        pbCode.onLastCleared = { bSendOtp.disable() }
    }

    private fun initClicks() {
        bSendOtp.setOnClickListener { sendCode() }
    }

    private fun subscribeLiveData() {
        bindDataTo(viewModel.successLiveData) { makeActionAsResult { navigator.goSelect(requireContext(), it) } }
        bindDataTo(viewModel.errorLiveData) { makeActionAsResult { showSnack(it) } }
    }

    private fun makeActionAsResult(action: () -> Unit) {
        progress.gone()
        bSendOtp.enable()
        action.invoke()
    }

    companion object {

        private const val TOKEN_KEY = "token"

        fun newInstance(token: String) = OtpFragment().apply {
            arguments = bundleOf(TOKEN_KEY to token)
        }
    }
}