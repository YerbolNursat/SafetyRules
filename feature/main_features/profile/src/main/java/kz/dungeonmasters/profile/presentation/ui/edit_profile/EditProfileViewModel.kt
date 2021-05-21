package kz.dungeonmasters.profile.presentation.ui.edit_profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kz.dungeonmasters.core.core_application.presentation.viewModel.CoreLaunchViewModel
import kz.dungeonmasters.core.core_application.utils.events.Event
import kz.dungeonmasters.profile.data.entity.ProfileInfo
import kz.dungeonmasters.profile.domain.GetProfileInfoUseCase
import kz.dungeonmasters.profile.domain.GetProfilePhotosUseCase
import kz.dungeonmasters.profile.domain.SetProfileInfoUseCase
import kz.dungeonmasters.profile.presentation.ui.models_bottom.ProfileImageUi
import kz.dungeonmasters.profile.presentation.ui.models_bottom.ProfileImagesUi

class EditProfileViewModel(
    private val getProfileInfoUseCase: GetProfileInfoUseCase,
    private val setProfileInfoUseCase: SetProfileInfoUseCase,
    private val getProfilePhotosUseCase: GetProfilePhotosUseCase
) : CoreLaunchViewModel() {

    private val _items = MutableLiveData<ProfileInfo>()
    val items: LiveData<ProfileInfo>
        get() = _items

    lateinit var listOfItems: ProfileImagesUi

    val newDestination = MutableLiveData<Event<Unit>>()
    val setImage = MutableLiveData<Event<String>>()

    var imageId: String? = null

    private val _photos = MutableLiveData<Event<ProfileImagesUi>>()
    val photos: LiveData<Event<ProfileImagesUi>>
        get() = _photos


    fun getProfilePhotos() {
        launch({ getProfilePhotosUseCase.execute(Unit) }, {
            it?.let {
                val profileImagesUi = ProfileImagesUi(
                    listOfImages = it.results.map {
                        ProfileImageUi(
                            it.id,
                            it.photo,
                            onClick = ::onProfilePhotoClick
                        )
                    },
                    actionOnClick = ::setImage
                )
                listOfItems = profileImagesUi
                _photos.postValue(Event(profileImagesUi))
            }
        })
    }

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

    private fun setImage(url: String, id: String) {
        setImage.postValue(Event(url))
        imageId = id
    }

    private fun onProfilePhotoClick(id: String) {
        listOfItems.listOfImages.forEach { profileImageUi ->
            profileImageUi.value.picked = profileImageUi.id == id
        }
        listOfItems.setVisibleButton()
    }

}