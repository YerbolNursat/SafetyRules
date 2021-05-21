package kz.dungeonmasters.profile.domain

import kz.dungeonmasters.core.core_application.data.network.ResultApi
import kz.dungeonmasters.core.core_application.domain.CoreUseCase
import kz.dungeonmasters.profile.data.entity.ProfilePhotosResponse
import kz.dungeonmasters.profile.data.repository.ProfileRepository

class GetProfilePhotosUseCase(
    private val profileRepository: ProfileRepository
) : CoreUseCase<Unit, ProfilePhotosResponse> {

    override suspend fun execute(param: Unit): ResultApi<ProfilePhotosResponse> {
        return profileRepository.getPhotos()
    }


}