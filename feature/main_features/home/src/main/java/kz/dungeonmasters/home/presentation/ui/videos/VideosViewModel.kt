package kz.dungeonmasters.home.presentation.ui.videos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kz.dungeonmasters.core.core_application.presentation.viewModel.CoreLaunchViewModel
import kz.dungeonmasters.core.core_application.utils.extensions.transformFromOneObjectToAnother
import kz.dungeonmasters.home.domain.usecase.GetVideosUseCase
import kz.dungeonmasters.home.presentation.ui.models.ComicsUi
import kz.dungeonmasters.home.presentation.ui.models.VideoUi

class VideosViewModel(
    private val getVideosUseCase: GetVideosUseCase
) : CoreLaunchViewModel() {

    private val _items = MutableLiveData<List<VideoUi>>()
    val items: LiveData<List<VideoUi>>
        get() = _items

    fun getVideos(param: String) {
        launch({
            getVideosUseCase.execute(param)
        }, {
            it?.let {
                _items.postValue(it.results.map { result ->
                    val comicsUi: VideoUi = result.transformFromOneObjectToAnother()
                    comicsUi.maxSize = true
                    comicsUi
                })
            }
        })
    }

}