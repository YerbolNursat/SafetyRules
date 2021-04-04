package kz.telecom.core.core_application.presentation.ui.widget.toolbar

import kz.telecom.core.core_application.R

class MainToolbar(
    var text: String,
    var actionOnClick: (() -> Unit)? = null,
    val needShowNameOfPage: Boolean = true,
    var needShowMoreOption: Boolean = false,
    var needShowSpinnerArrow: Boolean = false,
    var moreOptionIcon: Int = R.drawable.ic_defailt_more_options,
    var actionOnOptionClick: (() -> Unit)? = null,
) {

    fun onClick() {
        actionOnClick?.invoke()
    }


    fun onOptionClick() {
        actionOnOptionClick?.invoke()
    }
}