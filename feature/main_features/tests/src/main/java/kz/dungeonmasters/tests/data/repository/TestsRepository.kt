package kz.dungeonmasters.tests.data.repository

import kz.dungeonmasters.core.core_application.data.network.ResultApi
import kz.dungeonmasters.core.core_application.utils.network.safeApiCall
import kz.dungeonmasters.tests.data.entity.CategoryResponse
import kz.dungeonmasters.tests.data.entity.TestAnswersResponse
import kz.dungeonmasters.tests.data.entity.TestQuestionsResponse
import kz.dungeonmasters.tests.data.entity.TestsResponse
import kz.dungeonmasters.tests.data.remote.TestsApi
import kz.dungeonmasters.tests.domain.usecase.CheckTestUseCase
import okhttp3.ResponseBody

class TestsRepository(private val testsApi: TestsApi) {

    suspend fun getCategories(): ResultApi<CategoryResponse> =
        safeApiCall { testsApi.getCategories() }

    suspend fun getTests(params: String): ResultApi<TestsResponse> =
        safeApiCall { testsApi.getTests(params) }

    suspend fun getTestQuestions(params: String): ResultApi<TestQuestionsResponse> =
        safeApiCall { testsApi.getTestQuestions(params) }

    suspend fun checkTestQuestions(params: CheckTestUseCase.ParamsResponse): ResultApi<TestAnswersResponse> =
        safeApiCall { testsApi.checkTestQuestions(params) }

}