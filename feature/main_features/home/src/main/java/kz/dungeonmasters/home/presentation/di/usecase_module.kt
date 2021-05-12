package kz.dungeonmasters.home.presentation.di


import kz.dungeonmasters.home.domain.usecase.GetCategories
import org.koin.dsl.module

val mainHomeUseCaseModule = module {
    single { GetCategories(homeRepository = get()) }
}
