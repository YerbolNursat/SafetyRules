package kz.dungeonmasters.profile.domain

import kz.dungeonmasters.core.core_application.data.network.ResultApi
import kz.dungeonmasters.core.core_application.domain.CoreUseCase
import kz.dungeonmasters.profile.data.entity.ProfileInfo
import kz.dungeonmasters.profile.data.repository.ProfileRepository
import okhttp3.ResponseBody

class SetProfileInfoUseCase(
    private val profileRepository: ProfileRepository
) : CoreUseCase<ProfileInfo, ResponseBody> {

    override suspend fun execute(param: ProfileInfo): ResultApi<ResponseBody> {
        return profileRepository.setProfileInfo(param)
    }


}