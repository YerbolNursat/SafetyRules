package kz.dungeonmasters.tests.data.remote

import kz.dungeonmasters.tests.data.entity.CategoryResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface TestsApi {

    @GET("/quizzes/categories/")
    suspend fun getCategories(): CategoryResponse

}