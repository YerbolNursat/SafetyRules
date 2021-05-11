package kz.dungeonmasters.auth.data.remote

import kz.dungeonmasters.auth.data.entity.LoginToken
import kz.dungeonmasters.auth.domain.usecase.LoginUseCase
import kz.dungeonmasters.auth.domain.usecase.RegisterSendEmailUseCase
import kz.dungeonmasters.auth.domain.usecase.RegisterUseCase
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("/auth/register-request/")
    suspend fun registerSendEmail(
        @Body body: RegisterSendEmailUseCase.Params
    )

    @POST("/auth/register/")
    suspend fun register(
        @Body body: RegisterUseCase.Params
    ): LoginToken

    @POST("/auth/login/")
    suspend fun login(
        @Body body: LoginUseCase.Params
    ): LoginToken

}