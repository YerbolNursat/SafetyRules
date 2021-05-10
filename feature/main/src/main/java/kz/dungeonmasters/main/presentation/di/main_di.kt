package kz.dungeonmasters.main.presentation.di

import kz.dungeonmasters.home.presentation.di.mainHomeNetworkModule
import kz.dungeonmasters.home.presentation.di.mainHomeRepositoryModule
import kz.dungeonmasters.home.presentation.di.mainHomeUseCaseModule
import kz.dungeonmasters.home.presentation.di.mainHomeViewModelModule
import kz.dungeonmasters.messages.presentation.di.mainMessagesNetworkModule
import kz.dungeonmasters.messages.presentation.di.mainMessagesRepositoryModule
import kz.dungeonmasters.messages.presentation.di.mainMessagesUseCaseModule
import kz.dungeonmasters.messages.presentation.di.mainMessagesViewModelModule
import kz.dungeonmasters.profile.presentation.di.mainProfileNetworkModule
import kz.dungeonmasters.profile.presentation.di.mainProfileRepositoryModule
import kz.dungeonmasters.profile.presentation.di.mainProfileUseCaseModule
import kz.dungeonmasters.profile.presentation.di.mainProfileViewModelModule
import kz.dungeonmasters.search.presentation.di.mainSearchNetworkModule
import kz.dungeonmasters.search.presentation.di.mainSearchRepositoryModule
import kz.dungeonmasters.search.presentation.di.mainSearchUseCaseModule
import kz.dungeonmasters.search.presentation.di.mainSearchViewModelModule
import kz.dungeonmasters.tests.presentation.di.mainTestsNetworkModule
import kz.dungeonmasters.tests.presentation.di.mainTestsRepositoryModule
import kz.dungeonmasters.tests.presentation.di.mainTestsUseCaseModule
import kz.dungeonmasters.tests.presentation.di.mainTestsViewModelModule


val mainModules = listOf(
    mainHomeViewModelModule,
    mainHomeUseCaseModule,
    mainHomeRepositoryModule,
    mainHomeNetworkModule,

    mainMessagesViewModelModule,
    mainMessagesUseCaseModule,
    mainMessagesRepositoryModule,
    mainMessagesNetworkModule,

    mainSearchViewModelModule,
    mainSearchUseCaseModule,
    mainSearchRepositoryModule,
    mainSearchNetworkModule,

    mainProfileViewModelModule,
    mainProfileUseCaseModule,
    mainProfileRepositoryModule,
    mainProfileNetworkModule,

    mainTestsViewModelModule,
    mainTestsUseCaseModule,
    mainTestsRepositoryModule,
    mainTestsNetworkModule,

    )

