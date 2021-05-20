package kz.dungeonmasters.tests.domain.usecase

import kz.dungeonmasters.core.core_application.data.network.ResultApi
import kz.dungeonmasters.core.core_application.domain.CoreUseCase
import kz.dungeonmasters.tests.data.entity.TestQuestionsResponse
import kz.dungeonmasters.tests.data.repository.TestsRepository

class GetTestQuestionsUseCase(
    private val testsRepository: TestsRepository
) : CoreUseCase<String, TestQuestionsResponse> {

    override suspend fun execute(param: String): ResultApi<TestQuestionsResponse> {
        return testsRepository.getTestQuestions(param)
    }


}