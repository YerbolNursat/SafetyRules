package kz.dungeonmasters.home.data.repository

import kz.dungeonmasters.core.core_application.data.network.ResultApi
import kz.dungeonmasters.core.core_application.utils.network.safeApiCall
import kz.dungeonmasters.home.data.entity.CategoryResponse
import kz.dungeonmasters.home.data.remote.HomeApi

class HomeRepository(private val homeApi: HomeApi) {

    suspend fun getCategories(): ResultApi<CategoryResponse> =
        safeApiCall { homeApi.getCategories() }

}