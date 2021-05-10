package kz.dungeonmasters.auth.presentation.di

import kz.dungeonmasters.auth.data.repository.AuthRepository
import org.koin.dsl.module

val authRepositoryModule = module {
    single { AuthRepository(authApi = get()) }
}