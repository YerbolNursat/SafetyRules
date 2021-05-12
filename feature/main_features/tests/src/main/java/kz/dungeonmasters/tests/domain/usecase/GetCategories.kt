package kz.dungeonmasters.tests.domain.usecase

import kz.dungeonmasters.core.core_application.data.network.ResultApi
import kz.dungeonmasters.core.core_application.domain.CoreUseCase
import kz.dungeonmasters.tests.data.entity.CategoryResponse
import kz.dungeonmasters.tests.data.repository.TestsRepository

class GetCategories(
    private val testsRepository: TestsRepository
) : CoreUseCase<Any, CategoryResponse> {

    override suspend fun execute(param: Any): ResultApi<CategoryResponse> {
        return testsRepository.getCategories()
    }


}