package kz.dungeonmasters.tests.presentation.di


import kz.dungeonmasters.tests.domain.usecase.GetCategoriesUseCase
import kz.dungeonmasters.tests.domain.usecase.GetTestsUseCase
import org.koin.dsl.module

val mainTestsUseCaseModule = module {
    single { GetCategoriesUseCase(testsRepository = get()) }
    single { GetTestsUseCase(testsRepository = get()) }
}
