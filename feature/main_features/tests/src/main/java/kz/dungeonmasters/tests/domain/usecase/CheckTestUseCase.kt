package kz.dungeonmasters.tests.domain.usecase

import kz.dungeonmasters.core.core_application.data.network.ResultApi
import kz.dungeonmasters.core.core_application.domain.CoreUseCase
import kz.dungeonmasters.tests.data.entity.TestAnswersResponse
import kz.dungeonmasters.tests.data.repository.TestsRepository
import okhttp3.ResponseBody

class CheckTestUseCase(
    private val testsRepository: TestsRepository
) : CoreUseCase<CheckTestUseCase.ParamsResponse, TestAnswersResponse> {

    override suspend fun execute(param: ParamsResponse): ResultApi<TestAnswersResponse> {
        return testsRepository.checkTestQuestions(param)
    }

    data class ParamsResponse(
        val quiz: String,
        val accepted_answers: List<Params>
    )

    data class Params(
        val option: String,
        val question: String
    )

}