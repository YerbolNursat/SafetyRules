package kz.dungeonmasters.auth.presentation.di

import kz.dungeonmasters.auth.presentation.ui.welcome.AuthViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val authViewModelModule = module {
    viewModel { AuthViewModel(registerSendEmailUseCase = get(),registerUseCase = get(),loginUseCase = get()) }
}