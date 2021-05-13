package kz.dungeonmasters.home.presentation.ui.models

import android.view.View
import androidx.databinding.ViewDataBinding
import com.xwray.groupie.viewbinding.BindableItem
import kz.dungeonmasters.home.R
import kz.dungeonmasters.home.databinding.ItemTestsBinding

class TestsUi(
    val id: Int,
    val title: String?,
    val score: Int,
    val body: String?
) : BindableItem<ViewDataBinding>() {
    override fun bind(viewBinding: ViewDataBinding, position: Int) {
        when (viewBinding) {
            is ItemTestsBinding -> {
                viewBinding.item = this
            }
        }
    }

    override fun getLayout(): Int = R.layout.item_tests
    override fun initializeViewBinding(view: View): ViewDataBinding =
        ItemTestsBinding.bind(view)
}