package kz.dungeonmasters.home.presentation.di

import kz.dungeonmasters.home.data.repository.HomeRepository
import org.koin.dsl.module

val mainHomeRepositoryModule = module {
    single { HomeRepository(homeApi = get()) }

}