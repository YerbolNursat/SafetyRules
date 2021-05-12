package kz.dungeonmasters.tests.presentation.di

import kz.dungeonmasters.core.core_application.di.createWebService
import kz.dungeonmasters.tests.data.remote.TestsApi
import org.koin.dsl.module


val mainTestsNetworkModule = module {
    single { createWebService<TestsApi>(okHttpClient = get()) }

}