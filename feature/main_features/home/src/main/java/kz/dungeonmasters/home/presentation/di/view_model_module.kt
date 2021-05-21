package kz.dungeonmasters.home.presentation.di

import kz.dungeonmasters.home.presentation.ui.comics.ComicsViewModel
import kz.dungeonmasters.home.presentation.ui.home.HomeViewModel
import kz.dungeonmasters.home.presentation.ui.instructions.InstructionsViewModel
import kz.dungeonmasters.home.presentation.ui.pdf_viewer.PdfViewerViewModel
import kz.dungeonmasters.home.presentation.ui.theory.TheoryViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainHomeViewModelModule = module {

    viewModel { HomeViewModel(getCategoriesUseCase = get()) }
    viewModel { TheoryViewModel(getTheoryUseCase = get(),getStaticFileUseCase = get(),getArticlesDetailUseCase = get()) }
    viewModel { PdfViewerViewModel() }
    viewModel { ComicsViewModel(getStaticFileUseCase = get(),getComicsUseCase = get()) }
    viewModel { InstructionsViewModel(getArticlesUseCase = get(),getArticlesDetailUseCase = get()) }

}