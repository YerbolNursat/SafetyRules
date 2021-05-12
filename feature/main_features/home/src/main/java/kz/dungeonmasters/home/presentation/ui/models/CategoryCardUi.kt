package kz.dungeonmasters.home.presentation.ui.models

import android.view.View
import androidx.databinding.ViewDataBinding
import com.xwray.groupie.viewbinding.BindableItem
import kz.dungeonmasters.home.R
import kz.dungeonmasters.home.databinding.ItemCategoryCardBinding

data class CategoryCardUi(
    val icon: String,
    val size: Int,
    val is_active: Boolean,
    val title: String,
    val code: String
) : BindableItem<ViewDataBinding>() {
    override fun bind(viewBinding: ViewDataBinding, position: Int) {
        when (viewBinding) {
            is ItemCategoryCardBinding -> {
                viewBinding.item = this
            }
        }
    }

    override fun getLayout(): Int = R.layout.item_category_card
    override fun initializeViewBinding(view: View): ViewDataBinding =
        ItemCategoryCardBinding.bind(view)
}