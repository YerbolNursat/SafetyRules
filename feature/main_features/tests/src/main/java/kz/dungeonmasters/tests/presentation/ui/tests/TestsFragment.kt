package kz.dungeonmasters.tests.presentation.ui.tests

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kz.dungeonmasters.core.core_application.presentation.content.CoreSimpleToolbar
import kz.dungeonmasters.core.core_application.presentation.ui.fragments.CoreFragment
import kz.dungeonmasters.core.core_application.utils.extensions.standardInitSimpleToolbar
import kz.dungeonmasters.tests.BR
import kz.dungeonmasters.tests.R
import kz.dungeonmasters.tests.databinding.FragmentTestsBinding
import kz.dungeonmasters.tests.presentation.ui.models.TestsWithScoreCardUi
import org.koin.androidx.viewmodel.ext.android.viewModel

class TestsFragment : CoreFragment<FragmentTestsBinding, TestsViewModel>() {

    private val groupAdapter = GroupAdapter<GroupieViewHolder>()

    override val viewModel: TestsViewModel by viewModel()

    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutRes(): Int = R.layout.fragment_tests

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val categoryCode = arguments?.get("CategoryCode") as String
        viewModel.getItems(categoryCode)

        initToolbar()
        initViews()
    }

    private fun initViews() {
        binding.testsRv.adapter = groupAdapter
    }

    private fun initToolbar() {
        standardInitSimpleToolbar(CoreSimpleToolbar("Тесты"), binding.toolbar.root)
    }

    override fun onStart() {
        super.onStart()
        viewModel.items.observe(viewLifecycleOwner, Observer(::handleItems))
    }

    private fun handleItems(data: List<TestsWithScoreCardUi>) {
        groupAdapter.updateAsync(data)
        groupAdapter.setOnItemClickListener { item, view ->
            findNavController().navigate(R.id.action_testsFragment_to_testDetailFragment)
        }

    }
}