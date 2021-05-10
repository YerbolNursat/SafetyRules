package kz.dungeonmasters.auth.domain.usecase

import kz.dungeonmasters.auth.data.repository.AuthRepository
import kz.dungeonmasters.core.core_application.data.network.ResultApi
import kz.dungeonmasters.core.core_application.domain.CoreUseCase

class RegisterSendEmailUseCase(
    private val authRepository: AuthRepository
) : CoreUseCase<RegisterSendEmailUseCase.Params, Any> {

    override suspend fun execute(param: Params): ResultApi<Any> {
        return authRepository.registerSendEmail(param)
    }

    data class Params(
        val email: String
    )

}