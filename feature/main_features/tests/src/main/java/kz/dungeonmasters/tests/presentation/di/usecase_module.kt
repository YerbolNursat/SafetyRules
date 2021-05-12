package kz.dungeonmasters.tests.presentation.di


import kz.dungeonmasters.tests.domain.usecase.GetCategories
import org.koin.dsl.module

val mainTestsUseCaseModule = module {
    single { GetCategories(testsRepository = get()) }
}
