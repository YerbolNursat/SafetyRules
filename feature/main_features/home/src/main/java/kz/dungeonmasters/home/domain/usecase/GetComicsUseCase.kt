package kz.dungeonmasters.home.domain.usecase

import kz.dungeonmasters.core.core_application.data.network.ResultApi
import kz.dungeonmasters.core.core_application.domain.CoreUseCase
import kz.dungeonmasters.home.data.entity.Comics
import kz.dungeonmasters.home.data.repository.HomeRepository

class GetComicsUseCase(
    private val homeRepository: HomeRepository
) : CoreUseCase<String, Comics> {

    override suspend fun execute(param: String): ResultApi<Comics> {
        return homeRepository.getComics(param)
    }


}