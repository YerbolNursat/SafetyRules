package kz.telecom.core.core_application.presentation.content

class CoreButton(
    val text: String,
    val actionOnClick: (() -> Unit),
    val iconDrawable: Int? = null
) {

    fun onClick() {
        actionOnClick()
    }
}
