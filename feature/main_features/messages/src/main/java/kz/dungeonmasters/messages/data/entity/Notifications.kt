package kz.dungeonmasters.messages.data.entity

data class NotificationResponse(
    val results: List<Notifications>
)

data class Notifications(
    val id: String,
    val title: String?,
    val body: String?,
    val is_read: Boolean,
    val created_at: String,
)