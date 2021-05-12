package kz.dungeonmasters.tests.data.repository

import kz.dungeonmasters.core.core_application.data.network.ResultApi
import kz.dungeonmasters.core.core_application.utils.network.safeApiCall
import kz.dungeonmasters.tests.data.entity.CategoryResponse
import kz.dungeonmasters.tests.data.remote.TestsApi

class TestsRepository(private val testsApi: TestsApi) {

    suspend fun getCategories(): ResultApi<CategoryResponse> =
        safeApiCall { testsApi.getCategories() }

}