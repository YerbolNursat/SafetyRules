package kz.dungeonmasters.home.presentation.ui.comics

import android.Manifest
import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.github.florent37.runtimepermission.RuntimePermission
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.viewbinding.BindableItem
import kz.dungeonmasters.core.core_application.presentation.content.CoreSimpleToolbar
import kz.dungeonmasters.core.core_application.presentation.ui.fragments.CoreFragment
import kz.dungeonmasters.core.core_application.utils.events.EventObserver
import kz.dungeonmasters.core.core_application.utils.extensions.standardInitSimpleToolbar
import kz.dungeonmasters.home.BR
import kz.dungeonmasters.home.R
import kz.dungeonmasters.home.databinding.FragmentComicsBinding
import kz.dungeonmasters.home.presentation.ui.models.ComicsUi
import kz.dungeonmasters.home.presentation.ui.pdf_viewer.PdfLoaded
import kz.dungeonmasters.home.presentation.ui.pdf_viewer.PdfViewerFragmentArgs
import kz.dungeonmasters.home.presentation.ui.theory.PdfDownload
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class ComicsFragment : CoreFragment<FragmentComicsBinding, ComicsViewModel>() {
    private lateinit var categoryCode: String

    private val groupAdapter = GroupAdapter<GroupieViewHolder>()
    override val viewModel: ComicsViewModel by viewModel()

    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutRes(): Int = R.layout.fragment_comics

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.comicsRv.adapter = groupAdapter
        categoryCode = arguments?.get("CategoryCode") as String
        viewModel.getItems(categoryCode)
        initToolbar()
    }

    private fun initToolbar() {
        standardInitSimpleToolbar(
            CoreSimpleToolbar("Комиксы"),
            binding.toolbar.root
        )
    }

    override fun onStart() {
        super.onStart()
        viewModel.items.observe(viewLifecycleOwner,Observer(::handleItems))
        viewModel.startDownload.observe(viewLifecycleOwner, EventObserver(::startDownload))
        viewModel.pdfLoaded.observe(viewLifecycleOwner, EventObserver(::navigateToPdfViewer))
    }

    private fun handleItems(data: List<BindableItem<ViewDataBinding>>) {
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
            R.id.action_comicsFragment_to_pdfViewerFragment,
            PdfViewerFragmentArgs(pdfLoaded.filePath, pdfLoaded.title).toBundle()
        )

    }
}