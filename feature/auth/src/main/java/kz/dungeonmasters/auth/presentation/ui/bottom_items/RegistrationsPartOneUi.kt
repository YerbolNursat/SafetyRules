package kz.dungeonmasters.auth.presentation.ui.bottom_items

import android.view.View
import androidx.databinding.ViewDataBinding
import kz.application.auth.R
import kz.application.auth.databinding.ItemBottomRegistrationPartOneBinding
import kz.dungeonmasters.core.core_application.presentation.content.CoreButton
import kz.dungeonmasters.core.core_application.presentation.ui.dialogs.BaseBottomSheetDialogItem
import kz.dungeonmasters.core.core_application.utils.extensions.standardInitButton

class RegistrationsPartOneUi():BaseBottomSheetDialogItem() {
    override fun bind(viewBinding: ViewDataBinding, position: Int) {
        when (viewBinding) {
            is ItemBottomRegistrationPartOneBinding -> {
                viewBinding.item = this
                standardInitButton(CoreButton("Далле", {}), viewBinding.btnNext.root)
            }
        }
    }

    override fun getLayout(): Int = R.layout.item_bottom_registration_part_one
    override fun initializeViewBinding(view: View): ViewDataBinding =
        ItemBottomRegistrationPartOneBinding.bind(view)
}