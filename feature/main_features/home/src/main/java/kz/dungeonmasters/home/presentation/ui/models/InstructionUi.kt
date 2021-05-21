package kz.dungeonmasters.home.presentation.ui.models

import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.xwray.groupie.viewbinding.BindableItem
import kz.dungeonmasters.home.R
import kz.dungeonmasters.home.databinding.ItemInstructionBinding

class InstructionUi(
    val code: String,
    val title: String?,
    val body: String?,
    val maxSize: Boolean? = false,
    var navigateToDetail: (() -> Unit)? = null
) : BindableItem<ViewDataBinding>() {
    override fun bind(viewBinding: ViewDataBinding, position: Int) {
        when (viewBinding) {
            is ItemInstructionBinding -> {
                viewBinding.item = this
                if (maxSize == true) {
                    val params: ViewGroup.MarginLayoutParams =
                        viewBinding.root.layoutParams as ViewGroup.MarginLayoutParams
                    params.width = ViewGroup.LayoutParams.MATCH_PARENT
                }
            }
        }
    }

    override fun getLayout(): Int = R.layout.item_instruction
    override fun initializeViewBinding(view: View): ViewDataBinding =
        ItemInstructionBinding.bind(view)
}