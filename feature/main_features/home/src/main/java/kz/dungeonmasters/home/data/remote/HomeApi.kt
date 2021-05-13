package kz.dungeonmasters.home.data.remote

import kz.dungeonmasters.home.data.entity.CategoryResponse
import kz.dungeonmasters.home.data.entity.Theory
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path

interface HomeApi {

    @GET("/topics/")
    suspend fun getCategories(): CategoryResponse

    @GET("/topics/{topic_code}/")
    suspend fun getTheory(
        @Path("topic_code") topic_code: String
    ): Theory

    @GET("/files/download/{code}")
    suspend fun getStaticFiles(
        @Path("code") filename: String,
    ): ResponseBody

}