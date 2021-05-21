package kz.dungeonmasters.home.presentation.di


import kz.dungeonmasters.home.domain.usecase.*
import org.koin.dsl.module

val mainHomeUseCaseModule = module {

    single { GetCategoriesUseCase(homeRepository = get()) }
    single { GetTheoryUseCase(homeRepository = get()) }
    single { GetStaticFileUseCase(homeRepository = get()) }
    single { GetArticlesUseCase(homeRepository = get()) }
    single { GetArticlesDetailUseCase(homeRepository = get()) }
    single { GetComicsUseCase(homeRepository = get()) }

}
