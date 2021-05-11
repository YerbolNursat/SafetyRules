package kz.dungeonmasters.auth.data.entity

data class LoginToken (
    val refresh:String,
    val access:String,
)