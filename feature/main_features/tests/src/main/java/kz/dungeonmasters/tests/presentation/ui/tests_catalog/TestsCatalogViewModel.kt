package kz.dungeonmasters.tests.presentation.ui.tests_catalog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kz.dungeonmasters.core.core_application.presentation.viewModel.CoreLaunchViewModel
import kz.dungeonmasters.core.core_application.utils.extensions.transformFromOneObjectToAnother
import kz.dungeonmasters.tests.domain.usecase.GetCategoriesUseCase
import kz.dungeonmasters.tests.presentation.ui.models.CategoryCardUi

class TestsCatalogViewModel(
    private val getCategoriesUseCase: GetCategoriesUseCase
) : CoreLaunchViewModel() {

    private val _items = MutableLiveData<List<CategoryCardUi>>()
    val items: LiveData<List<CategoryCardUi>>
        get() = _items

    fun getItems() {
        launch({ getCategoriesUseCase.execute(Any()) }, {
            it?.let {
                _items.postValue(it.results.map { result -> result.transformFromOneObjectToAnother() })
            }
        })
    }
}