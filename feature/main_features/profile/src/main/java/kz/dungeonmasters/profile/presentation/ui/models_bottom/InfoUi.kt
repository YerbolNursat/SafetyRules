package kz.dungeonmasters.profile.presentation.ui.models_bottom

import android.view.View
import androidx.databinding.ViewDataBinding
import kz.dungeonmasters.core.core_application.presentation.ui.dialogs.BaseBottomSheetDialogItem
import kz.dungeonmasters.profile.R
import kz.dungeonmasters.profile.databinding.ItemBottomInfoBinding

data class InfoUi(
    val title: String,
    val subtitle: String,
    val text: String
) : BaseBottomSheetDialogItem() {
    override fun bind(viewBinding: ViewDataBinding, position: Int) {
        when (viewBinding) {
            is ItemBottomInfoBinding -> {
                viewBinding.item = this
            }
        }
    }

    override fun getLayout(): Int = R.layout.item_bottom_info
    override fun initializeViewBinding(view: View): ViewDataBinding =
        ItemBottomInfoBinding.bind(view)
}