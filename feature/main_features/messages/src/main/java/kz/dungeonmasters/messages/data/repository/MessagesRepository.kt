package kz.dungeonmasters.messages.data.repository

import kz.dungeonmasters.core.core_application.data.network.ResultApi
import kz.dungeonmasters.core.core_application.utils.network.safeApiCall
import kz.dungeonmasters.messages.data.entity.NotificationResponse
import kz.dungeonmasters.messages.data.entity.Notifications
import kz.dungeonmasters.messages.data.remote.MessagesApi

class MessagesRepository(private val messagesApi: MessagesApi) {

    suspend fun getNotifications(params: Unit): ResultApi<NotificationResponse> = safeApiCall {
        messagesApi.getNotifications()
    }

    suspend fun getNotificationDetail(params: String): ResultApi<Notifications> = safeApiCall {
        messagesApi.getNotificationDetail(params)
    }

}