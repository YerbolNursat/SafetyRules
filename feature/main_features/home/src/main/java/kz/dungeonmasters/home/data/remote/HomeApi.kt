package kz.dungeonmasters.home.data.remote

import kz.dungeonmasters.home.data.entity.CategoryResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface HomeApi {

    @GET("/topics/")
    suspend fun getCategories(): CategoryResponse

}