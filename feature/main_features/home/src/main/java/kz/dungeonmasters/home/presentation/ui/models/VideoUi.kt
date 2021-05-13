package kz.dungeonmasters.home.presentation.ui.models

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentManager
import com.xwray.groupie.viewbinding.BindableItem
import kz.dungeonmasters.home.R
import kz.dungeonmasters.home.databinding.ItemVideoBinding
import kz.dungeonmasters.home.presentation.ui.video.VideoDialog

class VideoUi(
    val code: String?,
    val title: String?,
    val url: String,
    val icon: String,
    var fm:FragmentManager?=null
) : BindableItem<ViewDataBinding>() {
    override fun bind(viewBinding: ViewDataBinding, position: Int) {
        when (viewBinding) {
            is ItemVideoBinding -> {
                viewBinding.item = this
            }
        }
    }

    override fun getLayout(): Int = R.layout.item_video
    override fun initializeViewBinding(view: View): ViewDataBinding = ItemVideoBinding.bind(view)

    fun onClick(){
        VideoDialog(fm!!,url)
    }
}