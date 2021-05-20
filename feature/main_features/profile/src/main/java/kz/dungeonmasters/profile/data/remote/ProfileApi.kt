package kz.dungeonmasters.profile.data.remote

import kz.dungeonmasters.profile.data.entity.ProfileInfo
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT

interface ProfileApi {

    @GET("/auth/user-profile/")
    suspend fun getProfileInfo(): ProfileInfo

    @PUT("/auth/user-profile/")
    suspend fun setProfileInfo(@Body body: ProfileInfo): ResponseBody

}