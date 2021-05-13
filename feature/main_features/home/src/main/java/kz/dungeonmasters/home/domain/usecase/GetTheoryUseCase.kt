package kz.dungeonmasters.home.domain.usecase

import kz.dungeonmasters.core.core_application.data.network.ResultApi
import kz.dungeonmasters.core.core_application.domain.CoreUseCase
import kz.dungeonmasters.home.data.entity.Theory
import kz.dungeonmasters.home.data.repository.HomeRepository

class GetTheoryUseCase(
    private val homeRepository: HomeRepository
) : CoreUseCase<String, Theory> {

    override suspend fun execute(param: String): ResultApi<Theory> {
        return homeRepository.getCategories(param)
    }


}