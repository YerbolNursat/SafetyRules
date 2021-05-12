package kz.dungeonmasters.home.domain.usecase

import kz.dungeonmasters.core.core_application.data.network.ResultApi
import kz.dungeonmasters.core.core_application.domain.CoreUseCase
import kz.dungeonmasters.home.data.entity.CategoryResponse
import kz.dungeonmasters.home.data.repository.HomeRepository

class GetCategories(
    private val homeRepository: HomeRepository
) : CoreUseCase<Any, CategoryResponse> {

    override suspend fun execute(param: Any): ResultApi<CategoryResponse> {
        return homeRepository.getCategories()
    }


}