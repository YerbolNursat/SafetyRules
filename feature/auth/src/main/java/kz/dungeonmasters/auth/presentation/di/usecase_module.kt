package kz.dungeonmasters.auth.presentation.di


import kz.dungeonmasters.auth.domain.usecase.LoginUseCase
import kz.dungeonmasters.auth.domain.usecase.RegisterSendEmailUseCase
import kz.dungeonmasters.auth.domain.usecase.RegisterUseCase
import org.koin.dsl.module

val authUseCaseModule = module {

    single { RegisterSendEmailUseCase(authRepository = get()) }
    single { RegisterUseCase(authRepository = get()) }
    single { LoginUseCase(authRepository = get()) }

}
