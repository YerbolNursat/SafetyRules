package kz.dungeonmasters.core.core_application.presentation.ui.dialogs

import androidx.databinding.ViewDataBinding
import com.xwray.groupie.viewbinding.BindableItem

abstract class BaseBottomSheetDialogItem : BindableItem<ViewDataBinding>() {
    var actionForClose: (() -> Unit)? = null
}