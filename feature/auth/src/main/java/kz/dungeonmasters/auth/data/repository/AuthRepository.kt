package kz.dungeonmasters.auth.data.repository

import kz.dungeonmasters.auth.data.remote.AuthApi
import kz.dungeonmasters.auth.domain.usecase.LoginUseCase
import kz.dungeonmasters.auth.domain.usecase.RegisterSendEmailUseCase
import kz.dungeonmasters.auth.domain.usecase.RegisterUseCase
import kz.dungeonmasters.core.core_application.data.network.ResultApi
import kz.dungeonmasters.core.core_application.utils.network.safeApiCall

class AuthRepository(private val authApi: AuthApi) {

    suspend fun registerSendEmail(params: RegisterSendEmailUseCase.Params):ResultApi<Any> =
        safeApiCall { authApi.registerSendEmail(params) }

    suspend fun register(params: RegisterUseCase.Params):ResultApi<Any> =
        safeApiCall { authApi.register(params) }

    suspend fun login(params: LoginUseCase.Params):ResultApi<Any> =
        safeApiCall { authApi.login(params) }

}