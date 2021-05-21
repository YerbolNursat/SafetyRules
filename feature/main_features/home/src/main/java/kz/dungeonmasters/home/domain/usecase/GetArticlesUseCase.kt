package kz.dungeonmasters.home.domain.usecase

import kz.dungeonmasters.core.core_application.data.network.ResultApi
import kz.dungeonmasters.core.core_application.domain.CoreUseCase
import kz.dungeonmasters.home.data.entity.Articles
import kz.dungeonmasters.home.data.repository.HomeRepository

class GetArticlesUseCase(
    private val homeRepository: HomeRepository
) : CoreUseCase<String, Articles> {

    override suspend fun execute(param: String): ResultApi<Articles> {
        return homeRepository.getArticles(param)
    }


}