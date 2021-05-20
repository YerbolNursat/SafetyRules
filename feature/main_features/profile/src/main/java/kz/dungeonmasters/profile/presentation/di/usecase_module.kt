package kz.dungeonmasters.profile.presentation.di


import kz.dungeonmasters.profile.domain.GetProfileInfoUseCase
import kz.dungeonmasters.profile.domain.SetProfileInfoUseCase
import org.koin.dsl.module

val mainProfileUseCaseModule = module {
    single { GetProfileInfoUseCase(profileRepository = get()) }
    single { SetProfileInfoUseCase(profileRepository = get()) }
}
