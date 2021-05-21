package kz.dungeonmasters.messages.domain.usecase

import kz.dungeonmasters.core.core_application.data.network.ResultApi
import kz.dungeonmasters.core.core_application.domain.CoreUseCase
import kz.dungeonmasters.messages.data.entity.Notifications
import kz.dungeonmasters.messages.data.remote.MessagesApi
import kz.dungeonmasters.messages.data.repository.MessagesRepository

class GetNotificationsDetailUseCase(
    private val messagesRepository: MessagesRepository
) : CoreUseCase<String, Notifications> {

    override suspend fun execute(param: String): ResultApi<Notifications> {
        return messagesRepository.getNotificationDetail(param)
    }


}