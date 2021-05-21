package kz.dungeonmasters.home.data.remote

import kz.dungeonmasters.home.data.entity.*
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

    @GET("/topics/{topic_code}/articles/")
    suspend fun getArticles(
        @Path("topic_code") topic_code: String
    ): Articles

    @GET("/topics/{topic_code}/comics/")
    suspend fun getComics(
        @Path("topic_code") topic_code: String
    ): Comics

    @GET("/topics/{topic_code}/videos/")
    suspend fun getVideos(
        @Path("topic_code") topic_code: String
    ): Videos

    @GET("/topics/{topic_code}/articles/{article_code}/")
    suspend fun getArticleDetail(
        @Path("topic_code") topic_code: String,
        @Path("article_code") article_code: String,
    ): Article

    @GET("/files/download/{code}")
    suspend fun getStaticFiles(
        @Path("code") filename: String,
    ): ResponseBody

}