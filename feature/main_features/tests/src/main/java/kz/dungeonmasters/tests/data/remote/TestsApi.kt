package kz.dungeonmasters.tests.data.remote

import kz.dungeonmasters.tests.data.entity.CategoryResponse
import kz.dungeonmasters.tests.data.entity.TestsResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface TestsApi {

    @GET("/quizzes/categories/")
    suspend fun getCategories(): CategoryResponse

    @GET("/quizzes/categories/{id}/")
    suspend fun getTests(@Path("id") id: String): TestsResponse

}