package kz.dungeonmasters.home.domain.usecase

import kz.dungeonmasters.core.core_application.data.network.ResultApi
import kz.dungeonmasters.core.core_application.domain.CoreUseCase
import kz.dungeonmasters.home.data.entity.Videos
import kz.dungeonmasters.home.data.repository.HomeRepository

class GetVideosUseCase(
    private val homeRepository: HomeRepository
) : CoreUseCase<String, Videos> {

    override suspend fun execute(param: String): ResultApi<Videos> {
        return homeRepository.getVideos(param)
    }


}