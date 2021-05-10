package kz.dungeonmasters.auth.presentation.ui.bottom_items

import android.view.View
import androidx.databinding.ViewDataBinding
import kz.application.auth.R
import kz.application.auth.databinding.ItemBottomRegistrationPartTwoBinding
import kz.dungeonmasters.auth.domain.usecase.RegisterSendEmailUseCase
import kz.dungeonmasters.auth.domain.usecase.RegisterUseCase
import kz.dungeonmasters.core.core_application.presentation.content.CoreButton
import kz.dungeonmasters.core.core_application.presentation.ui.dialogs.BaseBottomSheetDialogItem
import kz.dungeonmasters.core.core_application.utils.extensions.standardInitButton

class RegistrationPartTwoUi(
    private val actionOnClick: ((RegisterUseCase.Params) -> Unit)
) : BaseBottomSheetDialogItem() {
    lateinit var binding: ItemBottomRegistrationPartTwoBinding
    override fun bind(viewBinding: ViewDataBinding, position: Int) {
        when (viewBinding) {
            is ItemBottomRegistrationPartTwoBinding -> {
                viewBinding.item = this
                binding = viewBinding
                standardInitButton(CoreButton("Войти", ::actionOnClick), viewBinding.btnLogIn.root)
            }
        }
    }

    override fun getLayout(): Int = R.layout.item_bottom_registration_part_two

    override fun initializeViewBinding(view: View): ViewDataBinding =
        ItemBottomRegistrationPartTwoBinding.bind(view)

    private fun actionOnClick() {
        actionOnClick.invoke(RegisterUseCase.Params(
            binding.etCode.text.toString(),
            null,
            binding.etPassword.text.toString(),
            binding.etPasswordConfirm.text.toString(),

        ))
    }

}