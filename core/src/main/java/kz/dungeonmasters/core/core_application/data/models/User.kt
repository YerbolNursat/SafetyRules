package kz.dungeonmasters.core.core_application.data.models

import com.google.gson.annotations.SerializedName


data class RefreshTokenRequestDTO(
    @SerializedName("refresh") val refresh_token: String,
)


data class AuthRefreshTokenDTO(
    @SerializedName("access") val access_token: String,
    @SerializedName("token_type") val token_type: String?,
    @SerializedName("refresh") val refresh_token: String,
    @SerializedName("expires_in") val expires_in: String,
    @SerializedName("scope") val scope: String,
    @SerializedName("session") val session: String,
    @SerializedName("operator") val operator: String,
    @SerializedName("status") val status: String,
    @SerializedName("jti") val jti: String
)
