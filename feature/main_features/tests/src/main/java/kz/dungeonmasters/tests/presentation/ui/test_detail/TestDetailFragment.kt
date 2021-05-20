package kz.dungeonmasters.tests.presentation.ui.test_detail

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kz.dungeonmasters.core.core_application.presentation.content.CoreButton
import kz.dungeonmasters.core.core_application.presentation.content.CoreSimpleToolbar
import kz.dungeonmasters.core.core_application.presentation.ui.fragments.CoreFragment
import kz.dungeonmasters.core.core_application.utils.extensions.standardInitButton
import kz.dungeonmasters.core.core_application.utils.extensions.standardInitSimpleToolbar
import kz.dungeonmasters.core.core_application.utils.extensions.visible
import kz.dungeonmasters.tests.BR
import kz.dungeonmasters.tests.R
import kz.dungeonmasters.tests.data.entity.TestQuestions
import kz.dungeonmasters.tests.databinding.FragmentTestDetailBinding
import kz.dungeonmasters.tests.domain.usecase.CheckTestUseCase
import kz.dungeonmasters.tests.presentation.ui.models.TestQuestionNumberUi
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class TestDetailFragment : CoreFragment<FragmentTestDetailBinding, TestDetailViewModel>() {
    private lateinit var clickedQuestion: TestQuestionNumberUi
    private val groupAdapter = GroupAdapter<GroupieViewHolder>()
    private var testId: String? = null

    override val viewModel: TestDetailViewModel by viewModel()

    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutRes(): Int = R.layout.fragment_test_detail

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        testId = arguments?.getString("TestId")
        viewModel.getTestQuestions(testId!!)
        initViews()
    }

    private fun initViews() {
        standardInitButton(
            CoreButton(
                "Завершить тест",
                { viewModel.checkTest(transformToParamsResponse()) }), binding.btnEndTest.root
        )
        binding.testDetailRv.adapter = groupAdapter
        binding.radioButtonOne.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                viewModel.listOfData.entries.first { it.key == clickedQuestion }.value.options.onEachIndexed { i, j ->
                    when (i) {
                        0 -> {
                            j.clicked = isChecked
                        }
                        1 -> {
                            j.clicked = !isChecked
                        }
                        2 -> {
                            j.clicked = !isChecked
                        }
                    }
                }
                binding.radioButtonTwo.isChecked = !isChecked
                binding.radioButtonThree.isChecked = !isChecked
            }
        }
        binding.radioButtonTwo.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                viewModel.listOfData.entries.first { it.key == clickedQuestion }.value.options.onEachIndexed { i, j ->
                    when (i) {
                        0 -> {
                            j.clicked = !isChecked
                        }
                        1 -> {
                            j.clicked = isChecked
                        }
                        2 -> {
                            j.clicked = !isChecked
                        }
                    }
                }
                binding.radioButtonOne.isChecked = !isChecked
                binding.radioButtonThree.isChecked = !isChecked
            }
        }
        binding.radioButtonThree.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                viewModel.listOfData.entries.first { it.key == clickedQuestion }.value.options.onEachIndexed { i, j ->
                    when (i) {
                        0 -> {
                            j.clicked = !isChecked
                        }
                        1 -> {
                            j.clicked = !isChecked
                        }
                        2 -> {
                            j.clicked = isChecked
                        }
                    }
                }
                binding.radioButtonOne.isChecked = !isChecked
                binding.radioButtonTwo.isChecked = !isChecked
            }
        }
    }

    private fun initToolbar(data: String) {
        standardInitSimpleToolbar(CoreSimpleToolbar(data), binding.toolbar.root)
    }

    override fun onStart() {
        super.onStart()
        viewModel.items.observe(viewLifecycleOwner, Observer(::handleItems))
        viewModel.toolbarText.observe(viewLifecycleOwner, Observer(::initToolbar))
        viewModel.isChecked.observe(viewLifecycleOwner, Observer(::initViewsAfterCheck))
    }

    private fun initViewsAfterCheck(data: Boolean) {
        if (data) {
            binding.radioButtonOne.isEnabled = false
            binding.radioButtonTwo.isEnabled = false
            binding.radioButtonThree.isEnabled = false
            standardInitButton(
                CoreButton(
                    "Пройти повторно тест",
                    { viewModel.getTestQuestions(testId.toString()) }), binding.btnEndTest.root
            )
        }
    }

    private fun handleItems(data: HashMap<TestQuestionNumberUi, TestQuestions>) {
        binding.radioButtonOne.isEnabled = true
        binding.radioButtonTwo.isEnabled = true
        binding.radioButtonThree.isEnabled = true
        groupAdapter.updateAsync(data.keys.toMutableList())
        groupAdapter.setOnItemClickListener { item, view ->
            setData(item as TestQuestionNumberUi)
            if (viewModel.isChecked.value == false) {
                data.keys.onEach {
                    if (it != item) {
                        it.value.numberColor = R.color.secondary
                    }
                }
            }
        }
        setData(data.entries.first().key)
    }

    private fun setData(item: TestQuestionNumberUi) {
        clickedQuestion = item
        with(viewModel.listOfData) {
            binding.simpleProgressBar.visible()
            if (viewModel.isChecked.value == false)
                item.value.numberColor = R.color.primary

            binding.titleTv.text = entries.first { item == it.key }.value.title
            var count = 0
            forEach { (i, j) ->
                j.options.forEachIndexed { position, testAnswers ->
                    if (i == item) {
                        when (position) {
                            0 -> {
                                binding.radioButtonOne.isChecked = testAnswers.clicked
                                binding.textOneTv.text = testAnswers.title
                            }
                            1 -> {
                                binding.radioButtonTwo.isChecked = testAnswers.clicked
                                binding.textTwoTv.text = testAnswers.title
                            }
                            2 -> {
                                binding.radioButtonThree.isChecked = testAnswers.clicked
                                binding.textThreeTv.text = testAnswers.title
                            }
                        }
                    }

                    if (testAnswers.clicked)
                        count++
                }
            }
            if (count * 100 / size == 100 && viewModel.isChecked.value == false) {
                binding.btnEndTest.root.visible()
            }
            binding.simpleProgressBar.progress = count * 100 / size

        }
    }

    private fun transformToParamsResponse(): CheckTestUseCase.ParamsResponse {
        val listOfParams = viewModel.listOfData.values.map {
            CheckTestUseCase.Params(
                it.id.toString(),
                it.options.first { it.clicked }.id.toString()
            )
        }
        return CheckTestUseCase.ParamsResponse(testId.toString(), listOfParams)
    }
}