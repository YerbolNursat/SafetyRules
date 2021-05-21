package kz.dungeonmasters.home.presentation.ui.models

import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.xwray.groupie.viewbinding.BindableItem
import kz.dungeonmasters.home.R
import kz.dungeonmasters.home.databinding.ItemInstructionsCardBinding

class InstructionCardUi(
    val actionToNavigate: (() -> Unit),
    val listOfInstructions: List<InstructionUi>,
) : BindableItem<ViewDataBinding>() {
    override fun bind(viewBinding: ViewDataBinding, position: Int) {
        when (viewBinding) {
            is ItemInstructionsCardBinding -> {
                viewBinding.item = this
            }
        }
    }

    override fun getLayout(): Int = R.layout.item_instructions_card
    override fun initializeViewBinding(view: View): ViewDataBinding =
        ItemInstructionsCardBinding.bind(view)
}