package kz.dungeonmasters.profile.domain

import kz.dungeonmasters.core.core_application.data.network.ResultApi
import kz.dungeonmasters.core.core_application.domain.CoreUseCase
import kz.dungeonmasters.profile.data.entity.ProfileInfo
import kz.dungeonmasters.profile.data.repository.ProfileRepository

class GetProfileInfoUseCase(
    private val profileRepository: ProfileRepository
) : CoreUseCase<Unit, ProfileInfo> {

    override suspend fun execute(param: Unit): ResultApi<ProfileInfo> {
        return profileRepository.getProfileInfo()
    }


}