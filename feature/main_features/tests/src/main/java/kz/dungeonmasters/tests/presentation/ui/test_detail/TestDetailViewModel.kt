package kz.dungeonmasters.tests.presentation.ui.test_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kz.dungeonmasters.core.core_application.presentation.viewModel.CoreLaunchViewModel
import kz.dungeonmasters.core.core_application.utils.events.Event
import kz.dungeonmasters.tests.R
import kz.dungeonmasters.tests.data.entity.TestAnswersResponse
import kz.dungeonmasters.tests.data.entity.TestQuestions
import kz.dungeonmasters.tests.domain.usecase.CheckTestUseCase
import kz.dungeonmasters.tests.domain.usecase.GetTestQuestionsUseCase
import kz.dungeonmasters.tests.presentation.ui.models.TestQuestionNumberUi

class TestDetailViewModel(
    private val getTestQuestionsUseCase: GetTestQuestionsUseCase,
    private val checkTestUseCase: CheckTestUseCase
) : CoreLaunchViewModel() {

    private val _items = MutableLiveData<HashMap<TestQuestionNumberUi, TestQuestions>>()
    val items: LiveData<HashMap<TestQuestionNumberUi, TestQuestions>>
        get() = _items

    lateinit var listOfData: HashMap<TestQuestionNumberUi, TestQuestions>

    val isChecked = MutableLiveData(false)
    val toolbarText = MutableLiveData<String>()
    val showDialog = MutableLiveData<Event<TestAnswersResponse>>()

    fun getTestQuestions(params: String) {
        launch({ getTestQuestionsUseCase.execute(params) }, {
            it?.let {
                isChecked.postValue(false)
                toolbarText.postValue(it.title)
                val data = hashMapOf<TestQuestionNumberUi, TestQuestions>()

                it.questions.forEachIndexed { i, testQuestions ->
                    if (i == 0) {
                        data[TestQuestionNumberUi((i + 1).toString(), R.color.primary)] =
                            testQuestions
                    } else {
                        data[TestQuestionNumberUi((i + 1).toString())] = testQuestions
                    }
                }
                val sortedMap: MutableMap<TestQuestionNumberUi, TestQuestions> = LinkedHashMap()
                data.entries.sortedBy { it.key.text.toInt() }
                    .forEach { sortedMap[it.key] = it.value }

                listOfData = sortedMap as HashMap<TestQuestionNumberUi, TestQuestions>
                _items.postValue(sortedMap)

            }
        })
    }

    fun checkTest(params: CheckTestUseCase.ParamsResponse) {
        launch({ checkTestUseCase.execute(params) }, {
            it?.let {
                listOfData.keys.onEachIndexed { index, testQuestionNumberUi ->
                    if (it.accepted_answers[index].is_correct) {
                        testQuestionNumberUi.value.numberColor = R.color.green
                    } else {
                        testQuestionNumberUi.value.numberColor = R.color.red
                    }
                }
                showDialog.postValue(Event(it))
                isChecked.postValue(true)
            }
        })
    }
}