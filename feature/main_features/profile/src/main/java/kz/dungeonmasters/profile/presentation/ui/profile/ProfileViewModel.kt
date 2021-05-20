package kz.dungeonmasters.profile.presentation.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kz.dungeonmasters.core.core_application.presentation.viewModel.CoreLaunchViewModel
import kz.dungeonmasters.profile.data.entity.ProfileInfo
import kz.dungeonmasters.profile.domain.GetProfileInfoUseCase

class ProfileViewModel(
    private val getProfileInfoUseCase: GetProfileInfoUseCase
) : CoreLaunchViewModel() {

    private val _items = MutableLiveData<ProfileInfo>()
    val items: LiveData<ProfileInfo>
        get() = _items


    fun getProfileInfo() {
        launch({ getProfileInfoUseCase.execute(Unit) }, {
            it?.let {
                _items.postValue(it)
            }
        })
    }
}