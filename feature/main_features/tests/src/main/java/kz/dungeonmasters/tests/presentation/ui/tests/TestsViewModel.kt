package kz.dungeonmasters.tests.presentation.ui.tests

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kz.dungeonmasters.core.core_application.presentation.viewModel.CoreLaunchViewModel
import kz.dungeonmasters.core.core_application.utils.extensions.transformFromOneObjectToAnother
import kz.dungeonmasters.tests.domain.usecase.GetCategories
import kz.dungeonmasters.tests.presentation.ui.models.CategoryCardUi

class TestsViewModel(
    private val getCategories: GetCategories
) : CoreLaunchViewModel() {
    private val _items = MutableLiveData<List<CategoryCardUi>>()
    val items: LiveData<List<CategoryCardUi>>
        get() = _items

    fun getItems() {
        launch({ getCategories.execute(Any()) }, {
            it?.let {
                _items.postValue(it.results.map { result -> result.transformFromOneObjectToAnother() })
            }
        })
    }
}