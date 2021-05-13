package kz.dungeonmasters.tests.presentation.ui.tests

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kz.dungeonmasters.core.core_application.presentation.viewModel.CoreLaunchViewModel
import kz.dungeonmasters.core.core_application.utils.extensions.transformFromOneObjectToAnother
import kz.dungeonmasters.tests.domain.usecase.GetTestsUseCase
import kz.dungeonmasters.tests.presentation.ui.models.TestsWithScoreCardUi

class TestsViewModel(
    private val getTestsUseCase: GetTestsUseCase
) : CoreLaunchViewModel() {

    private val _items = MutableLiveData<List<TestsWithScoreCardUi>>()
    val items: LiveData<List<TestsWithScoreCardUi>>
        get() = _items

    fun getItems(params: String) {
        launch({ getTestsUseCase.execute(params) }, {
            it?.let {
                _items.postValue(it.quizzes.map { result -> result.transformFromOneObjectToAnother() })
            }
        })
    }
}