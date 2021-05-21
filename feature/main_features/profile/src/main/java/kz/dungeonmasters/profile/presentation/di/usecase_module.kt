package kz.dungeonmasters.profile.presentation.di


import kz.dungeonmasters.profile.domain.GetAchievementUseCase
import kz.dungeonmasters.profile.domain.GetProfileInfoUseCase
import kz.dungeonmasters.profile.domain.GetProfilePhotosUseCase
import kz.dungeonmasters.profile.domain.SetProfileInfoUseCase
import org.koin.dsl.module

val mainProfileUseCaseModule = module {
    single { GetProfileInfoUseCase(profileRepository = get()) }
    single { SetProfileInfoUseCase(profileRepository = get()) }
    single { GetProfilePhotosUseCase(profileRepository = get()) }
    single { GetAchievementUseCase(profileRepository = get()) }
}
