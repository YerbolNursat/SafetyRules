package kz.dungeonmasters.profile.domain

import kz.dungeonmasters.core.core_application.data.network.ResultApi
import kz.dungeonmasters.core.core_application.domain.CoreUseCase
import kz.dungeonmasters.profile.data.entity.Achievement
import kz.dungeonmasters.profile.data.entity.ProfilePhotosResponse
import kz.dungeonmasters.profile.data.repository.ProfileRepository

class GetAchievementUseCase(
    private val profileRepository: ProfileRepository
) : CoreUseCase<Unit, List<Achievement>> {

    override suspend fun execute(param: Unit): ResultApi<List<Achievement>> {
        return profileRepository.getAchievements(param)
    }


}