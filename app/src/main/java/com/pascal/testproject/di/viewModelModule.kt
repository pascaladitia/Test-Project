package com.pascal.testproject.di

import com.pascal.testproject.viewmodel.AkademikViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        AkademikViewModel(
            application = androidApplication(),
            dao = get()
        )
    }
}
