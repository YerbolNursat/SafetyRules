package kz.dungeonmasters.profile.presentation.ui.achievements

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kz.dungeonmasters.core.core_application.presentation.viewModel.CoreLaunchViewModel
import kz.dungeonmasters.core.core_application.utils.dateForAchievements
import kz.dungeonmasters.core.core_application.utils.extensions.transformFromOneObjectToAnother
import kz.dungeonmasters.profile.domain.GetAchievementUseCase
import kz.dungeonmasters.profile.presentation.ui.models.AchievementsUi

class AchievementsViewModel(
    private val getAchievementUseCase: GetAchievementUseCase
) : CoreLaunchViewModel() {

    private val _items = MutableLiveData<List<AchievementsUi>>()
    val items: LiveData<List<AchievementsUi>>
        get() = _items


    fun getAchievementInfo() {
        launch({ getAchievementUseCase.execute(Unit) }, {
            it?.let {
                _items.postValue(it.map {
                    val data: AchievementsUi = it.transformFromOneObjectToAnother()
                    data.created_at = dateForAchievements(data.created_at) ?: ""
                    data
                })
            }
        })
    }
}