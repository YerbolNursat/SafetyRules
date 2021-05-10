package kz.dungeonmasters.auth.presentation.ui.bottom_items

import android.view.View
import androidx.databinding.ViewDataBinding
import kz.application.auth.R
import kz.application.auth.databinding.ItemBottomRegistrationPartOneBinding
import kz.dungeonmasters.auth.domain.usecase.RegisterSendEmailUseCase
import kz.dungeonmasters.core.core_application.presentation.content.CoreButton
import kz.dungeonmasters.core.core_application.presentation.ui.dialogs.BaseBottomSheetDialogItem
import kz.dungeonmasters.core.core_application.utils.extensions.standardInitButton

class RegistrationPartOneUi(
    private val actionOnClick: ((RegisterSendEmailUseCase.Params) -> Unit)
) : BaseBottomSheetDialogItem() {
    lateinit var binding: ItemBottomRegistrationPartOneBinding
    override fun bind(viewBinding: ViewDataBinding, position: Int) {
        when (viewBinding) {
            is ItemBottomRegistrationPartOneBinding -> {
                viewBinding.item = this
                binding=viewBinding
                standardInitButton(CoreButton("Далее", ::actionOnClick), viewBinding.btnNext.root)
            }
        }
    }

    override fun getLayout(): Int = R.layout.item_bottom_registration_part_one
    override fun initializeViewBinding(view: View): ViewDataBinding =
        ItemBottomRegistrationPartOneBinding.bind(view)

    private fun actionOnClick() {
        actionOnClick.invoke(RegisterSendEmailUseCase.Params(binding.etEmail.text.toString()))
    }
}