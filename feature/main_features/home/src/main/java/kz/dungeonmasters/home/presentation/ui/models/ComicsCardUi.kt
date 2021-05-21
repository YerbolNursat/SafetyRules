package kz.dungeonmasters.home.presentation.ui.models

import android.view.View
import androidx.databinding.ViewDataBinding
import com.xwray.groupie.viewbinding.BindableItem
import kz.dungeonmasters.home.R
import kz.dungeonmasters.home.databinding.ItemComicsCardBinding

class ComicsCardUi(
    val actionToNavigate: (() -> Unit),
    val listOfComics: List<ComicsUi>
) : BindableItem<ViewDataBinding>() {
    override fun bind(viewBinding: ViewDataBinding, position: Int) {
        when (viewBinding) {
            is ItemComicsCardBinding -> {
                viewBinding.item = this
            }
        }
    }

    override fun getLayout(): Int = R.layout.item_comics_card
    override fun initializeViewBinding(view: View): ViewDataBinding =
        ItemComicsCardBinding.bind(view)
}