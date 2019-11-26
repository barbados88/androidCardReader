package com.abank.idcard.presentation.di

import com.abank.idcard.presentation.screens.auth.otp.OtpNavigator
import com.abank.idcard.presentation.screens.auth.otp.OtpNavigatorImpl
import com.abank.idcard.presentation.screens.auth.otp.OtpViewModel
import com.abank.idcard.presentation.screens.auth.otp.OtpViewModelImpl
import com.abank.idcard.presentation.screens.auth.signin.SignInNavigator
import com.abank.idcard.presentation.screens.auth.signin.SignInNavigatorImpl
import com.abank.idcard.presentation.screens.auth.signin.SignInViewModel
import com.abank.idcard.presentation.screens.auth.signin.SignInViewModelImpl
import com.abank.idcard.presentation.screens.scan.container.ContainerNavigator
import com.abank.idcard.presentation.screens.scan.container.ContainerNavigatorImpl
import com.abank.idcard.presentation.screens.scan.nfcreader.NfcReaderNavigator
import com.abank.idcard.presentation.screens.scan.nfcreader.NfcReaderNavigatorImpl
import com.abank.idcard.presentation.screens.scan.passportdetails.PassportDetailsNavigator
import com.abank.idcard.presentation.screens.scan.passportdetails.PassportDetailsNavigatorImpl
import com.abank.idcard.presentation.screens.scan.passportdetails.PassportDetailsViewModel
import com.abank.idcard.presentation.screens.scan.passportdetails.PassportDetailsViewModelImpl
import com.abank.idcard.presentation.screens.scan.scanner.ScannerNavigator
import com.abank.idcard.presentation.screens.scan.scanner.ScannerNavigatorImpl
import com.abank.idcard.presentation.screens.scan.select.SelectNavigator
import com.abank.idcard.presentation.screens.scan.select.SelectNavigatorImpl
import com.abank.idcard.presentation.screens.scan.select.SelectViewModel
import com.abank.idcard.presentation.screens.scan.select.SelectViewModelImpl
import com.abank.idcard.presentation.screens.splash.SplashViewModel
import com.abank.idcard.presentation.screens.splash.SplashViewModelImpl
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.Module
import org.koin.dsl.module.module

val viewModelModule: Module = module {
    factory<SignInViewModel> { SignInViewModelImpl(get()) }
    viewModel<OtpViewModel> { OtpViewModelImpl(get()) }
    viewModel<SplashViewModel> { SplashViewModelImpl(get()) }
    viewModel<SelectViewModel> { SelectViewModelImpl(get(), get()) }
    viewModel<PassportDetailsViewModel> { PassportDetailsViewModelImpl(get()) }
}

val navigatorModule: Module = module {
    factory<SignInNavigator> { SignInNavigatorImpl() }
    factory<OtpNavigator> { OtpNavigatorImpl() }
    factory<SelectNavigator> { SelectNavigatorImpl() }
    factory<ContainerNavigator> { ContainerNavigatorImpl() }
    factory<ScannerNavigator> { ScannerNavigatorImpl() }
    factory<NfcReaderNavigator> { NfcReaderNavigatorImpl() }
    factory<PassportDetailsNavigator> { PassportDetailsNavigatorImpl() }
}
