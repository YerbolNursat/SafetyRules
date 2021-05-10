package kz.dungeonmasters.auth.presentation.ui.bottom_items

import android.view.View
import androidx.databinding.ViewDataBinding
import kz.application.auth.R
import kz.application.auth.databinding.ItemBottomLogInBinding
import kz.dungeonmasters.core.core_application.presentation.content.CoreButton
import kz.dungeonmasters.core.core_application.presentation.ui.dialogs.BaseBottomSheetDialogItem
import kz.dungeonmasters.core.core_application.utils.extensions.standardInitButton

class LogInUi(
    private val actionOnClickLogIn: (() -> Unit)
) : BaseBottomSheetDialogItem() {
    override fun bind(viewBinding: ViewDataBinding, position: Int) {
        when (viewBinding) {
            is ItemBottomLogInBinding -> {
                viewBinding.item = this
                standardInitButton(CoreButton("Вход", ::actionOnClickLogIn), viewBinding.btnLogIn.root)
            }
        }
    }

    override fun getLayout(): Int = R.layout.item_bottom_log_in
    override fun initializeViewBinding(view: View): ViewDataBinding =
        ItemBottomLogInBinding.bind(view)

    private fun actionOnClickLogIn() {
        actionForClose?.invoke()
        actionOnClickLogIn.invoke()
    }

}