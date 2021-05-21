package kz.dungeonmasters.messages.data.remote

import kz.dungeonmasters.messages.data.entity.NotificationResponse
import kz.dungeonmasters.messages.data.entity.Notifications
import retrofit2.http.GET
import retrofit2.http.Path

interface MessagesApi {

    @GET("/auth/user-profile/notifications/")
    suspend fun getNotifications(): NotificationResponse

    @GET("/auth/user-profile/notifications/{id}")
    suspend fun getNotificationDetail(@Path("id") id: String): Notifications

}