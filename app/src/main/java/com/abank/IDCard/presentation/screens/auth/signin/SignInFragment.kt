package com.abank.idcard.presentation.screens.auth.signin

import android.os.Bundle
import android.view.View
import com.abank.idcard.R
import com.abank.idcard.presentation.base.BaseFragment
import com.abank.idcard.utils.Extensions.bindDataTo
import com.abank.idcard.utils.Extensions.*
import io.reactivex.rxkotlin.Observables
import kotlinx.android.synthetic.main.fragment_sign_in.*
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.viewModel

class SignInFragment : BaseFragment() {

    override fun getLayout() = R.layout.fragment_sign_in

    override val navigator: SignInNavigator = get()

    private val viewModel: SignInViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeTextChanges()
        subscribeClicks()
        subscribeLiveData()
    }

    private fun subscribeTextChanges() {
        Observables.combineLatest(etLogin.textObserver(), etPassword.textObserver())
        { login, password -> login.isValid(RegexValidation.phone).and(password.isValid(RegexValidation.password)) }
        .subscribe { bSignIn.isEnabled = it }
        .addtoBag()
    }

    private fun subscribeClicks() {
        bSignIn.setOnClickListener {
            progress.visible()
            bSignIn.disable()
            viewModel.authUser(etLogin.text.toString(), etPassword.text.toString())
        }
    }

    private fun subscribeLiveData() {
        bindDataTo(viewModel.signedInLiveData) { makeActionAsResult { navigator.goOtp(it, bSignIn, passwordInputLayout, tvTitle, ivLogo) } }
        bindDataTo(viewModel.errorLiveData) { makeActionAsResult {showSnack(it) } }
    }

    private fun makeActionAsResult(action: () -> Unit) {
        progress.gone()
        bSignIn.enable()
        action.invoke()
    }


}