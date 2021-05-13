package kz.dungeonmasters.home.presentation.ui.models

import android.view.View
import androidx.databinding.ViewDataBinding
import com.xwray.groupie.viewbinding.BindableItem
import kz.dungeonmasters.home.R
import kz.dungeonmasters.home.databinding.ItemTestsCardBinding

class TestsCardUi(
    val listOfTests: List<TestsUi>
) : BindableItem<ViewDataBinding>() {
    override fun bind(viewBinding: ViewDataBinding, position: Int) {
        when (viewBinding) {
            is ItemTestsCardBinding -> {
                viewBinding.item = this
            }
        }
    }

    override fun getLayout(): Int = R.layout.item_tests_card
    override fun initializeViewBinding(view: View): ViewDataBinding =
        ItemTestsCardBinding.bind(view)
}