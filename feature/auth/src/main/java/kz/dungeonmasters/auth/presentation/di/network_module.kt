package kz.dungeonmasters.auth.presentation.di

import kz.dungeonmasters.auth.data.remote.AuthApi
import kz.dungeonmasters.core.core_application.di.createWebService
import org.koin.dsl.module


val authNetworkModule = module {
    single { createWebService<AuthApi>(okHttpClient = get()) }

}