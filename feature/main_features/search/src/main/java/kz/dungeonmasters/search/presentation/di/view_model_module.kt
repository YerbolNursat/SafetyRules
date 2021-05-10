package kz.dungeonmasters.search.presentation.di

import kz.dungeonmasters.search.presentation.ui.search.SearchViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainSearchViewModelModule = module {
    viewModel { SearchViewModel() }
}