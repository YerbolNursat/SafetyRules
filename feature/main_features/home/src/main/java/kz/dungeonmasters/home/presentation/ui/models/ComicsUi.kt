package kz.dungeonmasters.home.presentation.ui.models

import android.view.View
import androidx.databinding.ViewDataBinding
import com.xwray.groupie.viewbinding.BindableItem
import kz.dungeonmasters.home.R
import kz.dungeonmasters.home.databinding.ItemComicsBinding

data class ComicsUi(
    val code: Int,
    val title: String,
    val url: String
) : BindableItem<ViewDataBinding>() {
    override fun bind(viewBinding: ViewDataBinding, position: Int) {
        when (viewBinding) {
            is ItemComicsBinding -> {
                viewBinding.item = this
            }
        }
    }

    override fun getLayout(): Int = R.layout.item_comics
    override fun initializeViewBinding(view: View): ViewDataBinding = ItemComicsBinding.bind(view)
}