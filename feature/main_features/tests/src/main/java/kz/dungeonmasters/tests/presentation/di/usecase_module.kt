package kz.dungeonmasters.tests.presentation.di


import kz.dungeonmasters.tests.domain.usecase.CheckTestUseCase
import kz.dungeonmasters.tests.domain.usecase.GetCategoriesUseCase
import kz.dungeonmasters.tests.domain.usecase.GetTestQuestionsUseCase
import kz.dungeonmasters.tests.domain.usecase.GetTestsUseCase
import org.koin.dsl.module

val mainTestsUseCaseModule = module {
    single { GetCategoriesUseCase(testsRepository = get()) }
    single { GetTestsUseCase(testsRepository = get()) }
    single { GetTestQuestionsUseCase(testsRepository = get()) }
    single { CheckTestUseCase(testsRepository = get()) }
}
