package kz.dungeonmasters.home.presentation.di

import kz.dungeonmasters.core.core_application.di.createWebService
import kz.dungeonmasters.home.data.remote.HomeApi
import org.koin.dsl.module


val mainHomeNetworkModule = module {
    single { createWebService<HomeApi>(okHttpClient = get()) }

}