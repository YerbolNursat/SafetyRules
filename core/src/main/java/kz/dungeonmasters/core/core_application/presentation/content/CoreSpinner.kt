package kz.dungeonmasters.core.core_application.presentation.content

class CoreSpinner<T : Any>(
    val actionOnSelect: ((T) -> Unit),
    val items: List<T>
)
