package kz.dungeonmasters.home.presentation.ui.pdf_viewer

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.content.FileProvider
import androidx.navigation.fragment.navArgs
import kz.dungeonmasters.core.core_application.presentation.content.CoreSimpleToolbar
import kz.dungeonmasters.core.core_application.presentation.ui.fragments.CoreFragment
import kz.dungeonmasters.core.core_application.utils.extensions.standardInitSimpleToolbar
import kz.dungeonmasters.home.BR
import kz.dungeonmasters.home.R
import kz.dungeonmasters.home.databinding.FragmentPdfViewerBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File


class PdfViewerFragment : CoreFragment<FragmentPdfViewerBinding, PdfViewerViewModel>() {
    private val args: PdfViewerFragmentArgs by navArgs()
    private val actionOnShare = {
        val uri = FileProvider.getUriForFile(
            requireContext(),
            requireContext().packageName.toString() + ".provider",
            File(args.filePath)
        )
        val shareIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_STREAM, uri)
            type = "application/pdf"
        }
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(Intent.createChooser(shareIntent, "Поделиться"))
    }
    override val viewModel: PdfViewerViewModel by viewModel()
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutRes(): Int = R.layout.fragment_pdf_viewer
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        standardInitSimpleToolbar(
            CoreSimpleToolbar(
                args.title,
                needShowMoreOption = true,
                moreOptionIcon = R.drawable.ic_share,
                actionOnOptionClick = actionOnShare
            ),
            binding.toolbar.root
        )
        binding.pdfViewer
            .fromFile(File(args.filePath))
            .load()
    }
}