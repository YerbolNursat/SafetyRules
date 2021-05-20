package kz.dungeonmasters.tests.data.remote

import kz.dungeonmasters.tests.data.entity.CategoryResponse
import kz.dungeonmasters.tests.data.entity.TestAnswersResponse
import kz.dungeonmasters.tests.data.entity.TestQuestionsResponse
import kz.dungeonmasters.tests.data.entity.TestsResponse
import kz.dungeonmasters.tests.domain.usecase.CheckTestUseCase
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface TestsApi {

    @GET("/quizzes/categories/")
    suspend fun getCategories(): CategoryResponse

    @GET("/quizzes/categories/{id}/")
    suspend fun getTests(@Path("id") id: String): TestsResponse

    @GET("/quizzes/{id}/")
    suspend fun getTestQuestions(@Path("id") id: String): TestQuestionsResponse

    @POST("/quizzes/submit-quiz/")
    suspend fun checkTestQuestions(@Body body: CheckTestUseCase.ParamsResponse): TestAnswersResponse

}