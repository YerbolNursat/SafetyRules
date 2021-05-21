package kz.dungeonmasters.home.data.repository

import kz.dungeonmasters.core.core_application.data.network.ResultApi
import kz.dungeonmasters.core.core_application.utils.network.safeApiCall
import kz.dungeonmasters.home.data.entity.*
import kz.dungeonmasters.home.data.remote.HomeApi
import kz.dungeonmasters.home.domain.usecase.GetArticlesDetailUseCase
import okhttp3.ResponseBody

class HomeRepository(private val homeApi: HomeApi) {

    suspend fun getCategories(): ResultApi<CategoryResponse> =
        safeApiCall { homeApi.getCategories() }

    suspend fun getCategories(params:String): ResultApi<Theory> =
        safeApiCall { homeApi.getTheory(params) }

    suspend fun getStaticFiles(param: String): ResultApi<ResponseBody> = safeApiCall {
        homeApi.getStaticFiles(param)
    }

    suspend fun getArticles(param: String): ResultApi<Articles> = safeApiCall {
        homeApi.getArticles(param)
    }

    suspend fun getComics(param: String): ResultApi<Comics> = safeApiCall {
        homeApi.getComics(param)
    }

    suspend fun getArticleDetail(param: GetArticlesDetailUseCase.Params): ResultApi<Article> = safeApiCall {
        homeApi.getArticleDetail(param.topic_code,param.article_code)
    }


}