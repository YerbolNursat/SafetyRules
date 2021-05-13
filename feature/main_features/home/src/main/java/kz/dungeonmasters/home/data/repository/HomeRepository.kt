package kz.dungeonmasters.home.data.repository

import kz.dungeonmasters.core.core_application.data.network.ResultApi
import kz.dungeonmasters.core.core_application.utils.network.safeApiCall
import kz.dungeonmasters.home.data.entity.CategoryResponse
import kz.dungeonmasters.home.data.entity.Theory
import kz.dungeonmasters.home.data.remote.HomeApi
import okhttp3.ResponseBody

class HomeRepository(private val homeApi: HomeApi) {

    suspend fun getCategories(): ResultApi<CategoryResponse> =
        safeApiCall { homeApi.getCategories() }

    suspend fun getCategories(params:String): ResultApi<Theory> =
        safeApiCall { homeApi.getTheory(params) }

    suspend fun getStaticFiles(param: String): ResultApi<ResponseBody> = safeApiCall {
        homeApi.getStaticFiles(param)
    }

}