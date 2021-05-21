package kz.dungeonmasters.messages.presentation.di

import kz.dungeonmasters.messages.presentation.ui.forum.ForumViewModel
import kz.dungeonmasters.messages.presentation.ui.messages.MessagesViewModel
import kz.dungeonmasters.messages.presentation.ui.notifications.NotificationsViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainMessagesViewModelModule = module {
    viewModel { MessagesViewModel() }
    viewModel { ForumViewModel() }
    viewModel { NotificationsViewModel(getNotificationsUseCase = get(),getNotificationsDetailUseCase = get()) }

}