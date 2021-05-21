package kz.dungeonmasters.core.core_application.presentation.model

import android.view.View
import androidx.databinding.ViewDataBinding
import kz.dungeonmasters.core.core_application.R
import kz.dungeonmasters.core.core_application.databinding.ItemBottomInfoBinding
import kz.dungeonmasters.core.core_application.presentation.ui.dialogs.BaseBottomSheetDialogItem

data class InfoResponseUi(
    val title: String,
    val listOfUi: List<InfoUi>
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