package kz.dungeonmasters.home.presentation.ui.theory

import android.Manifest
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.github.florent37.runtimepermission.RuntimePermission
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.viewbinding.BindableItem
import kz.dungeonmasters.core.core_application.presentation.content.CoreSimpleToolbar
import kz.dungeonmasters.core.core_application.presentation.model.InfoResponseUi
import kz.dungeonmasters.core.core_application.presentation.model.InfoUi
import kz.dungeonmasters.core.core_application.presentation.ui.dialogs.ModalBottomSheetDialog
import kz.dungeonmasters.core.core_application.presentation.ui.fragments.CoreFragment
import kz.dungeonmasters.core.core_application.utils.events.EventObserver
import kz.dungeonmasters.core.core_application.utils.extensions.standardInitSimpleToolbar
import kz.dungeonmasters.home.BR
import kz.dungeonmasters.home.R
import kz.dungeonmasters.home.databinding.FragmentTheoryBinding
import kz.dungeonmasters.home.presentation.ui.models.InstructionUi
import kz.dungeonmasters.home.presentation.ui.models.VideosCardUi
import kz.dungeonmasters.home.presentation.ui.pdf_viewer.PdfLoaded
import kz.dungeonmasters.home.presentation.ui.pdf_viewer.PdfViewerFragmentArgs
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class TheoryFragment : CoreFragment<FragmentTheoryBinding, TheoryViewModel>() {
    private lateinit var categoryCode:String
    private val groupAdapter = GroupAdapter<GroupieViewHolder>()

    override val viewModel: TheoryViewModel by viewModel()
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutRes(): Int = R.layout.fragment_theory

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        categoryCode = arguments?.get("CategoryCode") as String
        viewModel.getItems(categoryCode)

        initViews()
        initToolbar()

    }

    private fun initToolbar() {
        standardInitSimpleToolbar(
            CoreSimpleToolbar("Теория"),
            binding.toolbar.root
        )
    }

    private fun initViews() {
        binding.theoryRv.adapter = groupAdapter
    }

    override fun onStart() {
        super.onStart()
        viewModel.items.observe(viewLifecycleOwner, Observer(::handleItems))
        viewModel.startDownload.observe(viewLifecycleOwner, EventObserver(::startDownload))
        viewModel.pdfLoaded.observe(viewLifecycleOwner, EventObserver(::navigateToPdfViewer))
        viewModel.toComics.observe(viewLifecycleOwner, EventObserver(::navigateToComics))
        viewModel.toVideos.observe(viewLifecycleOwner, EventObserver(::navigateToVideos))
        viewModel.toInstructions.observe(
            viewLifecycleOwner,
            EventObserver(::navigateToInstructions)
        )
        viewModel.toTest.observe(viewLifecycleOwner, EventObserver(::navigateToTest))
        viewModel.toInstructionDetail.observe(
            viewLifecycleOwner,
            EventObserver(::navigateToInstructionDetail)
        )
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

    private fun navigateToTest(data: String) {
        navigator?.toTest(data)
    }

    private fun navigateToComics(data: Unit) {
        findNavController().navigate(
            R.id.action_theoryFragment_to_comicsFragment, bundleOf("CategoryCode" to categoryCode)
        )
    }

    private fun navigateToVideos(data: Unit) {
        findNavController().navigate(
            R.id.action_theoryFragment_to_videosFragment, bundleOf("CategoryCode" to categoryCode)
        )
    }

    private fun navigateToInstructions(data: Unit) {
        findNavController().navigate(
            R.id.action_theoryFragment_to_instructionsFragment,
            bundleOf("CategoryCode" to categoryCode)
        )
    }

    private fun handleItems(data: List<BindableItem<ViewDataBinding>>) {
        data.forEach {
            when (it) {
                is VideosCardUi -> {
                    it.listOfVideos.onEach { it.fm = requireActivity().supportFragmentManager }
                }
            }
        }
        groupAdapter.updateAsync(data)
    }

    private fun startDownload(data: PdfDownload) {
        Timber.i("startDownload")
        RuntimePermission.askPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .onAccepted {
                viewModel.loadPdf(requireContext(), data.fileName, data.title)
            }
            .ask()
    }

    private fun navigateToPdfViewer(pdfLoaded: PdfLoaded) {
        findNavController().navigate(
            R.id.action_theoryFragment_to_pdfViewerFragment,
            PdfViewerFragmentArgs(pdfLoaded.filePath, pdfLoaded.title).toBundle()
        )

    }
}