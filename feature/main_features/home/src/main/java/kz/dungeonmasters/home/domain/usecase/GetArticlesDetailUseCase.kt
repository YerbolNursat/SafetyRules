package kz.dungeonmasters.home.domain.usecase

import kz.dungeonmasters.core.core_application.data.network.ResultApi
import kz.dungeonmasters.core.core_application.domain.CoreUseCase
import kz.dungeonmasters.home.data.entity.Article
import kz.dungeonmasters.home.data.repository.HomeRepository

class GetArticlesDetailUseCase(
    private val homeRepository: HomeRepository
) : CoreUseCase<GetArticlesDetailUseCase.Params, Article> {

    override suspend fun execute(param: Params): ResultApi<Article> {
        return homeRepository.getArticleDetail(param)
    }

    data class Params(
        val topic_code: String,
        val article_code: String,
    )

}