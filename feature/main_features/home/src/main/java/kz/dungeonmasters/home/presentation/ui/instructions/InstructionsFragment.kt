package kz.dungeonmasters.home.presentation.ui.instructions

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kz.dungeonmasters.core.core_application.presentation.content.CoreSimpleToolbar
import kz.dungeonmasters.core.core_application.presentation.model.InfoResponseUi
import kz.dungeonmasters.core.core_application.presentation.model.InfoUi
import kz.dungeonmasters.core.core_application.presentation.ui.dialogs.ModalBottomSheetDialog
import kz.dungeonmasters.core.core_application.presentation.ui.fragments.CoreFragment
import kz.dungeonmasters.core.core_application.utils.events.EventObserver
import kz.dungeonmasters.core.core_application.utils.extensions.standardInitSimpleToolbar
import kz.dungeonmasters.home.BR
import kz.dungeonmasters.home.R
import kz.dungeonmasters.home.databinding.FragmentInstructionsBinding
import kz.dungeonmasters.home.presentation.ui.models.InstructionUi
import org.koin.androidx.viewmodel.ext.android.viewModel

class InstructionsFragment : CoreFragment<FragmentInstructionsBinding, InstructionsViewModel>() {
    private lateinit var categoryCode: String
    private val groupAdapter = GroupAdapter<GroupieViewHolder>()
    override val viewModel: InstructionsViewModel by viewModel()

    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutRes(): Int = R.layout.fragment_instructions

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        categoryCode = arguments?.get("CategoryCode") as String

        binding.instructionsRv.adapter = groupAdapter
        viewModel.getArticles(categoryCode)
        initToolbar()
    }

    private fun initToolbar() {
        standardInitSimpleToolbar(
            CoreSimpleToolbar("Инструкции"),
            binding.toolbar.root
        )
    }

    override fun onStart() {
        super.onStart()
        viewModel.items.observe(viewLifecycleOwner,Observer(::handleItems))
        viewModel.toInstructionDetail.observe(
            viewLifecycleOwner,
            EventObserver(::navigateToInstructionDetail)
        )
    }

    private fun handleItems(data: List<InstructionUi>) {
        groupAdapter.addAll(data)
    }

    private fun navigateToInstructionDetail(data: InfoResponseUi) {
        ModalBottomSheetDialog(
            listOf(
                data
            ),
            requireContext(),
            true,
            peekHide = resources.displayMetrics.heightPixels
        )
    }

}