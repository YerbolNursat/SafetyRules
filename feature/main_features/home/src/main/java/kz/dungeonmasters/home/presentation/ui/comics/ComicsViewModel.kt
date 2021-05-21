package kz.dungeonmasters.home.presentation.ui.comics

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kz.dungeonmasters.core.core_application.presentation.viewModel.CoreFileDownloadLaunchViewModel
import kz.dungeonmasters.core.core_application.utils.event.SingleLiveEvent
import kz.dungeonmasters.core.core_application.utils.events.Event
import kz.dungeonmasters.core.core_application.utils.extensions.transformFromOneObjectToAnother
import kz.dungeonmasters.home.domain.usecase.GetComicsUseCase
import kz.dungeonmasters.home.domain.usecase.GetStaticFileUseCase
import kz.dungeonmasters.home.presentation.ui.models.ComicsUi
import kz.dungeonmasters.home.presentation.ui.pdf_viewer.PdfLoaded
import kz.dungeonmasters.home.presentation.ui.theory.PdfDownload
import okhttp3.ResponseBody
import timber.log.Timber

class ComicsViewModel(
    private val getComicsUseCase: GetComicsUseCase,
    private val getStaticFileUseCase: GetStaticFileUseCase,
) : CoreFileDownloadLaunchViewModel() {

    private val _items = MutableLiveData<List<ComicsUi>>()
    val items: LiveData<List<ComicsUi>>
        get() = _items

    private val _startDownload = SingleLiveEvent<Event<PdfDownload>>()
    val startDownload: LiveData<Event<PdfDownload>>
        get() = _startDownload

    private val _pdfLoaded = SingleLiveEvent<Event<PdfLoaded>>()
    val pdfLoaded: LiveData<Event<PdfLoaded>>
        get() = _pdfLoaded

    private val actionToStartDownloadFile: (String, String) -> Unit = { s1, s2 ->
        _startDownload.postValue(Event(PdfDownload(s1, s2)))
    }

    fun getItems(params: String) {
        launch({ getComicsUseCase.execute(params) }, {
            it?.let {
                _items.postValue(it.results.map { result ->
                    val comicsUi: ComicsUi = result.transformFromOneObjectToAnother()
                    comicsUi.actionOnClick = actionToStartDownloadFile
                    comicsUi.maxSize = true
                    comicsUi
                })
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