package kz.dungeonmasters.messages.presentation.di

import kz.dungeonmasters.core.core_application.di.createWebService
import kz.dungeonmasters.messages.data.remote.MessagesApi
import org.koin.dsl.module


val mainMessagesNetworkModule = module {
    single { createWebService<MessagesApi>(okHttpClient = get()) }
}