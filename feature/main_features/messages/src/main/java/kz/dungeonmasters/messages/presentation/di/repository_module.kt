package kz.dungeonmasters.messages.presentation.di

import kz.dungeonmasters.messages.data.repository.MessagesRepository
import org.koin.dsl.module

val mainMessagesRepositoryModule = module {
    single { MessagesRepository(messagesApi = get()) }
}