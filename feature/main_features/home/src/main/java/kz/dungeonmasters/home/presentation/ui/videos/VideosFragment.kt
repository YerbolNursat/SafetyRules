package kz.dungeonmasters.home.presentation.ui.videos

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kz.dungeonmasters.core.core_application.presentation.content.CoreSimpleToolbar
import kz.dungeonmasters.core.core_application.presentation.ui.fragments.CoreFragment
import kz.dungeonmasters.core.core_application.utils.extensions.standardInitSimpleToolbar
import kz.dungeonmasters.home.BR
import kz.dungeonmasters.home.R
import kz.dungeonmasters.home.databinding.FragmentVideosBinding
import kz.dungeonmasters.home.presentation.ui.models.VideoUi
import org.koin.androidx.viewmodel.ext.android.viewModel

class VideosFragment : CoreFragment<FragmentVideosBinding, VideosViewModel>() {
    private lateinit var categoryCode: String

    private val groupAdapter = GroupAdapter<GroupieViewHolder>()

    override val viewModel: VideosViewModel by viewModel()

    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutRes(): Int = R.layout.fragment_videos

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.videosRv.adapter = groupAdapter
        categoryCode = arguments?.get("CategoryCode") as String
        viewModel.getVideos(categoryCode)
        initToolbar()
    }

    private fun initToolbar() {
        standardInitSimpleToolbar(
            CoreSimpleToolbar("Видео"),
            binding.toolbar.root
        )
    }

    override fun onStart() {
        super.onStart()
        viewModel.items.observe(viewLifecycleOwner, Observer(::handleItems))
    }

    private fun handleItems(data: List<VideoUi>) {
        groupAdapter.updateAsync(data)
    }

}