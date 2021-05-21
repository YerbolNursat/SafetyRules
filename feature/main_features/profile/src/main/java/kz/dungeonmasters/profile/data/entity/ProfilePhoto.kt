package kz.dungeonmasters.profile.data.entity

data class ProfilePhotosResponse(
    val results: List<ProfilePhoto>
)

class ProfilePhoto(
    val id: String,
    val photo: String
)