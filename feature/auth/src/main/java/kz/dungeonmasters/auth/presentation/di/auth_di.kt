package kz.dungeonmasters.auth.presentation.di

val authModules = listOf(
    authNetworkModule,
    authRepositoryModule,
    authUseCaseModule,
    authViewModelModule
)