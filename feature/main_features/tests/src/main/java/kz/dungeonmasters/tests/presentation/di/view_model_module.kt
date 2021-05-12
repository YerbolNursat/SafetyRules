package kz.dungeonmasters.tests.presentation.di

import kz.dungeonmasters.tests.presentation.ui.tests.TestsViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainTestsViewModelModule = module {
    viewModel { TestsViewModel(getCategories = get()) }
}