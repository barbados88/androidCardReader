package com.abank.IDCard.koin

import com.abank.IDCard.domain.ExampleViewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.Module
import org.koin.dsl.module.module

val viewModelModule: Module = module {
    viewModel { ExampleViewModel(get(), get(), get()) }
}
