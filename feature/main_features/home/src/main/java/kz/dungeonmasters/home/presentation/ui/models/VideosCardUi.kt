package kz.dungeonmasters.home.presentation.ui.models

import android.view.View
import androidx.databinding.ViewDataBinding
import com.xwray.groupie.viewbinding.BindableItem
import kz.dungeonmasters.home.R
import kz.dungeonmasters.home.databinding.ItemVideosCardBinding

data class VideosCardUi(
    val listOfVideos: List<VideoUi>
) : BindableItem<ViewDataBinding>() {
    override fun bind(viewBinding: ViewDataBinding, position: Int) {
        when (viewBinding) {
            is ItemVideosCardBinding -> {
                viewBinding.item = this
            }
        }
    }

    override fun getLayout(): Int = R.layout.item_videos_card
    override fun initializeViewBinding(view: View): ViewDataBinding =
        ItemVideosCardBinding.bind(view)
}