package kz.dungeonmasters.tests.domain.usecase

import kz.dungeonmasters.core.core_application.data.network.ResultApi
import kz.dungeonmasters.core.core_application.domain.CoreUseCase
import kz.dungeonmasters.tests.data.entity.TestsResponse
import kz.dungeonmasters.tests.data.repository.TestsRepository

class GetTestsUseCase(
    private val testsRepository: TestsRepository
) : CoreUseCase<String, TestsResponse> {

    override suspend fun execute(param: String): ResultApi<TestsResponse> {
        return testsRepository.getTests(param)
    }


}