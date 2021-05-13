package kz.dungeonmasters.home.presentation.di


import kz.dungeonmasters.home.domain.usecase.GetCategoriesUseCase
import kz.dungeonmasters.home.domain.usecase.GetStaticFileUseCase
import kz.dungeonmasters.home.domain.usecase.GetTheoryUseCase
import org.koin.dsl.module

val mainHomeUseCaseModule = module {

    single { GetCategoriesUseCase(homeRepository = get()) }
    single { GetTheoryUseCase(homeRepository = get()) }
    single { GetStaticFileUseCase(homeRepository = get()) }

}
