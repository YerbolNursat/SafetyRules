package kz.dungeonmasters.home.presentation.ui.theory

import android.content.Context
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.xwray.groupie.viewbinding.BindableItem
import kz.dungeonmasters.core.core_application.presentation.viewModel.CoreFileDownloadLaunchViewModel
import kz.dungeonmasters.core.core_application.utils.event.SingleLiveEvent
import kz.dungeonmasters.core.core_application.utils.events.Event
import kz.dungeonmasters.core.core_application.utils.extensions.transformFromOneObjectToAnother
import kz.dungeonmasters.home.domain.usecase.GetStaticFileUseCase
import kz.dungeonmasters.home.domain.usecase.GetTheoryUseCase
import kz.dungeonmasters.home.presentation.ui.models.*
import kz.dungeonmasters.home.presentation.ui.pdf_viewer.PdfLoaded
import okhttp3.ResponseBody
import timber.log.Timber

class TheoryViewModel(
    private val getTheoryUseCase: GetTheoryUseCase,
    private val getStaticFileUseCase: GetStaticFileUseCase
) : CoreFileDownloadLaunchViewModel() {

    private val _pdfLoaded = SingleLiveEvent<Event<PdfLoaded>>()
    val pdfLoaded: LiveData<Event<PdfLoaded>>
        get() = _pdfLoaded

    private val _items = MutableLiveData<List<BindableItem<ViewDataBinding>>>()
    val items: LiveData<List<BindableItem<ViewDataBinding>>>
        get() = _items

    private val _startDownload = SingleLiveEvent<Event<PdfDownload>>()
    val startDownload: LiveData<Event<PdfDownload>>
        get() = _startDownload

    private val actionToStartDownloadFile: (String, String) -> Unit = { s1, s2 ->
        _startDownload.postValue(Event(PdfDownload(s1, s2)))
    }

    fun getItems(params: String) {
        launch({ getTheoryUseCase.execute(params) }, {
            it?.let {
                val data = mutableListOf<BindableItem<ViewDataBinding>>()

                val listOfComics: List<ComicsUi> =
                    it.comics.map { comics ->
                        val comicsUi: ComicsUi = comics.transformFromOneObjectToAnother()
                        comicsUi.actionOnClick = actionToStartDownloadFile
                        comicsUi
                    }
                data.add(ComicsCardUi(listOfComics))

                val listOfVideos: List<VideoUi> =
                    it.videos.map { videos -> videos.transformFromOneObjectToAnother() }
                data.add(VideosCardUi(listOfVideos))

                val listOfInstructions: List<InstructionUi> =
                    it.articles.map { articles -> articles.transformFromOneObjectToAnother() }
                data.add(InstructionCardUi(listOfInstructions))

                val listOfTests: List<TestsUi> =
                    it.quizzes.map { quizzes -> quizzes.transformFromOneObjectToAnother() }
                data.add(TestsCardUi(listOfTests))

                _items.postValue(data)
            }
        })
    }

    fun loadPdf(context: Context, fileName: String, title: String) {
        launchWithError<ResponseBody, Any>({
            getStaticFileUseCase.execute(fileName)
        }, {
            it?.byteStream()?.let { inputStream ->
                val file = createFile(context, "Safety_Rules_$fileName", "pdf")
                file?.let {
                    copyStreamToFile(inputStream, file)
                    Timber.d("report saved to ${file.absolutePath}")
                    _pdfLoaded.postValue(
                        Event(
                            PdfLoaded(
                                filePath = file.absolutePath,
                                title = title
                            )
                        )
                    )
                }
            }
        })
    }

}