package kz.dungeonmasters.tests.presentation.di

import kz.dungeonmasters.tests.presentation.ui.test_detail.TestDetailViewModel
import kz.dungeonmasters.tests.presentation.ui.tests.TestsViewModel
import kz.dungeonmasters.tests.presentation.ui.tests_catalog.TestsCatalogViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainTestsViewModelModule = module {
    viewModel { TestsCatalogViewModel(getCategoriesUseCase = get()) }
    viewModel { TestsViewModel(getTestsUseCase = get()) }
    viewModel { TestDetailViewModel() }
}