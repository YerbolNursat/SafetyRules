package kz.dungeonmasters.home.presentation.ui.instructions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kz.dungeonmasters.core.core_application.presentation.model.InfoResponseUi
import kz.dungeonmasters.core.core_application.presentation.model.InfoUi
import kz.dungeonmasters.core.core_application.presentation.viewModel.CoreLaunchViewModel
import kz.dungeonmasters.core.core_application.utils.events.Event
import kz.dungeonmasters.home.domain.usecase.GetArticlesDetailUseCase
import kz.dungeonmasters.home.domain.usecase.GetArticlesUseCase
import kz.dungeonmasters.home.presentation.ui.models.InstructionUi

class InstructionsViewModel(
    private val getArticlesUseCase: GetArticlesUseCase,
    private val getArticlesDetailUseCase: GetArticlesDetailUseCase
) : CoreLaunchViewModel() {

    private val _items = MutableLiveData<List<InstructionUi>>()
    val items: LiveData<List<InstructionUi>>
        get() = _items

    val toInstructionDetail = MutableLiveData<Event<InfoResponseUi>>()

    fun getArticles(topicCode: String) {
        launch({ getArticlesUseCase.execute(topicCode) }, {
            it?.let {
                _items.postValue(
                    it.results.map {
                        val articlesUi = InstructionUi(
                            code = it.code,
                            title = it.title,
                            body = it.body,
                            maxSize = true,
                        )
                        articlesUi.navigateToDetail =
                            {
                                getArticleDetail(
                                    GetArticlesDetailUseCase.Params(
                                        topicCode,
                                        it.code
                                    )
                                )
                            }
                        articlesUi
                    }
                )
            }
        })
    }

    private fun getArticleDetail(params: GetArticlesDetailUseCase.Params) {
        launch({ getArticlesDetailUseCase.execute(params) }, {
            it?.let {
                toInstructionDetail.postValue(
                    Event(
                        InfoResponseUi(
                            title = "Инструкция",
                            listOf(
                                InfoUi(
                                    it.title ?: "",
                                    it.body ?: ""
                                )
                            )
                        )
                    )
                )
            }
        })
    }
}