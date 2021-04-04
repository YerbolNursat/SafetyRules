package kz.dungeonmasters.home.presentation.di

import kz.dungeonmasters.home.presentation.ui.home.HomeViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainHomeViewModelModule = module {
    viewModel { HomeViewModel() }
}