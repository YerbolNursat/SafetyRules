package kz.dungeonmasters.messages.presentation.di


import kz.dungeonmasters.messages.domain.usecase.GetNotificationsDetailUseCase
import kz.dungeonmasters.messages.domain.usecase.GetNotificationsUseCase
import org.koin.dsl.module

val mainMessagesUseCaseModule = module {

    single { GetNotificationsDetailUseCase(messagesRepository = get()) }
    single { GetNotificationsUseCase(messagesRepository = get()) }

}
