package kz.dungeonmasters.profile.presentation.ui.edit_profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kz.dungeonmasters.core.core_application.presentation.viewModel.CoreLaunchViewModel
import kz.dungeonmasters.core.core_application.utils.events.Event
import kz.dungeonmasters.profile.data.entity.ProfileInfo
import kz.dungeonmasters.profile.domain.GetProfileInfoUseCase
import kz.dungeonmasters.profile.domain.SetProfileInfoUseCase

class EditProfileViewModel(
    private val getProfileInfoUseCase: GetProfileInfoUseCase,
    private val setProfileInfoUseCase: SetProfileInfoUseCase
) : CoreLaunchViewModel() {

    private val _items = MutableLiveData<ProfileInfo>()
    val items: LiveData<ProfileInfo>
        get() = _items

    val newDestination = MutableLiveData<Event<Unit>>()

    fun getProfileInfo() {
        launch({ getProfileInfoUseCase.execute(Unit) }, {
            it?.let {
                _items.postValue(it)
            }
        })
    }

    fun setProfileInfo(params: ProfileInfo) {
        launch({ setProfileInfoUseCase.execute(params) }, {
            it?.let {
                newDestination.postValue(Event(Unit))
            }
        })
    }
}