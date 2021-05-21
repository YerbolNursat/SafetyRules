package kz.dungeonmasters.messages.domain.usecase

import kz.dungeonmasters.core.core_application.data.network.ResultApi
import kz.dungeonmasters.core.core_application.domain.CoreUseCase
import kz.dungeonmasters.messages.data.entity.NotificationResponse
import kz.dungeonmasters.messages.data.repository.MessagesRepository

class GetNotificationsUseCase(
    private val messagesRepository: MessagesRepository
) : CoreUseCase<Unit, NotificationResponse> {

    override suspend fun execute(param: Unit): ResultApi<NotificationResponse> {
        return messagesRepository.getNotifications(param)
    }

}