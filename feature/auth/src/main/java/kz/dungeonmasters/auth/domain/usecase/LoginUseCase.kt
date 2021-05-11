package kz.dungeonmasters.auth.domain.usecase

import kz.dungeonmasters.auth.data.entity.LoginToken
import kz.dungeonmasters.auth.data.repository.AuthRepository
import kz.dungeonmasters.core.core_application.data.network.ResultApi
import kz.dungeonmasters.core.core_application.domain.CoreUseCase

class LoginUseCase(
    private val authRepository: AuthRepository
) : CoreUseCase<LoginUseCase.Params, LoginToken> {

    override suspend fun execute(param: Params): ResultApi<LoginToken> {
        return authRepository.login(param)
    }

    data class Params(
        val email: String,
        val password: String,
    )

}