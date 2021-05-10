package kz.dungeonmasters.auth.domain.usecase

import kz.dungeonmasters.auth.data.repository.AuthRepository
import kz.dungeonmasters.core.core_application.data.network.ResultApi
import kz.dungeonmasters.core.core_application.domain.CoreUseCase

class RegisterUseCase(
    private val authRepository: AuthRepository
) : CoreUseCase<RegisterUseCase.Params, Any> {

    override suspend fun execute(param: Params): ResultApi<Any> {
        return authRepository.register(param)
    }

    data class Params(
        val verification_code: String,
        var email: String?,
        val password: String,
        val password_confirm: String
    )

}