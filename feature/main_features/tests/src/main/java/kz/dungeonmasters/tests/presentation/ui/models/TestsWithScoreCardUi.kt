package kz.dungeonmasters.tests.presentation.ui.models

import android.view.View
import androidx.databinding.ViewDataBinding
import com.xwray.groupie.viewbinding.BindableItem
import kz.dungeonmasters.tests.R
import kz.dungeonmasters.tests.databinding.ItemTestsWithScoreCardBinding

data class TestsWithScoreCardUi(
    val id: String,
    val title: String,
    val is_active: Boolean,
    val score: Int?,
    val body: String?,
) : BindableItem<ViewDataBinding>() {
    override fun bind(viewBinding: ViewDataBinding, position: Int) {
        when (viewBinding) {
            is ItemTestsWithScoreCardBinding -> {
                viewBinding.item = this
            }
        }
    }

    override fun getLayout(): Int = R.layout.item_tests_with_score_card
    override fun initializeViewBinding(view: View): ViewDataBinding =
        ItemTestsWithScoreCardBinding.bind(view)

}