package kz.dungeonmasters.tests.presentation.ui.models

import android.view.View
import androidx.databinding.ViewDataBinding
import com.xwray.groupie.viewbinding.BindableItem
import kz.dungeonmasters.tests.R
import kz.dungeonmasters.tests.databinding.ItemTestsCategoryCardBinding

data class CategoryCardUi(
    val icon: String,
    val size: Int,
    val is_active: Boolean,
    val title: String,
    val id: String
) : BindableItem<ViewDataBinding>() {
    override fun bind(viewBinding: ViewDataBinding, position: Int) {
        when (viewBinding) {
            is ItemTestsCategoryCardBinding -> {
                viewBinding.item = this
            }
        }
    }

    override fun getLayout(): Int = R.layout.item_tests_category_card
    override fun initializeViewBinding(view: View): ViewDataBinding =
        ItemTestsCategoryCardBinding.bind(view)
}