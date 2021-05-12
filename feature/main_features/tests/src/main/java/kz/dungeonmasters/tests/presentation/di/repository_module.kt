package kz.dungeonmasters.tests.presentation.di

import kz.dungeonmasters.tests.data.repository.TestsRepository
import org.koin.dsl.module

val mainTestsRepositoryModule = module {
    single { TestsRepository(testsApi = get()) }

}