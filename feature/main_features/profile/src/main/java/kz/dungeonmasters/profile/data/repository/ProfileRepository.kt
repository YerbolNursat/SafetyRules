package kz.dungeonmasters.profile.data.repository

import kz.dungeonmasters.core.core_application.data.network.ResultApi
import kz.dungeonmasters.core.core_application.utils.network.safeApiCall
import kz.dungeonmasters.profile.data.entity.ProfileInfo
import kz.dungeonmasters.profile.data.remote.ProfileApi
import okhttp3.ResponseBody

class ProfileRepository(private val profileApi: ProfileApi) {

    suspend fun getProfileInfo(): ResultApi<ProfileInfo> = safeApiCall {
        profileApi.getProfileInfo()
    }

    suspend fun setProfileInfo(params:ProfileInfo): ResultApi<ResponseBody> = safeApiCall {
        profileApi.setProfileInfo(params)
    }

}