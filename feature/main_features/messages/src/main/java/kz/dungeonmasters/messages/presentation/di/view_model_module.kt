package kz.dungeonmasters.messages.presentation.di

import kz.dungeonmasters.messages.presentation.ui.messages.MessagesViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainMessagesViewModelModule = module {
    viewModel { MessagesViewModel() }

}