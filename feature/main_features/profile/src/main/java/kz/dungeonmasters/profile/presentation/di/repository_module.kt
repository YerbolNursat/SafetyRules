package kz.dungeonmasters.profile.presentation.di

import kz.dungeonmasters.profile.data.repository.ProfileRepository
import org.koin.dsl.module

val mainProfileRepositoryModule = module {

    single { ProfileRepository(profileApi = get()) }

}