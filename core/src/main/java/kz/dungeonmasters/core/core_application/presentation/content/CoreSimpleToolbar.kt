package kz.dungeonmasters.core.core_application.presentation.content

import kz.dungeonmasters.core.core_application.R

class CoreSimpleToolbar(
    val text: String,
    var needShowNameOfPage: Boolean = true,
    val needShowMoreOption: Boolean = false,
    var needShowSearch: Boolean = false,
    var icBackIcon: Int = R.drawable.ic_default_back,
    var moreOptionIcon: Int = R.drawable.ic_defailt_more_options,
    var actionOnOptionClick: (() -> Unit)? = null,
) {
    fun onOptionClick() {
        actionOnOptionClick?.invoke()
    }
}