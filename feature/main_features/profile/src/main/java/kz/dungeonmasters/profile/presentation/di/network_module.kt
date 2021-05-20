package kz.dungeonmasters.profile.presentation.di

import kz.dungeonmasters.core.core_application.di.createWebService
import kz.dungeonmasters.profile.data.remote.ProfileApi
import org.koin.dsl.module


val mainProfileNetworkModule = module {
    single { createWebService<ProfileApi>(okHttpClient = get()) }

}