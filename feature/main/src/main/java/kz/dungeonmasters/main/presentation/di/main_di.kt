package kz.dungeonmasters.main.presentation.di

import kz.dungeonmasters.home.presentation.di.mainHomeNetworkModule
import kz.dungeonmasters.home.presentation.di.mainHomeRepositoryModule
import kz.dungeonmasters.home.presentation.di.mainHomeUseCaseModule
import kz.dungeonmasters.home.presentation.di.mainHomeViewModelModule


val mainModules = listOf(
    mainHomeViewModelModule,
    mainHomeUseCaseModule,
    mainHomeRepositoryModule,
    mainHomeNetworkModule,
)

