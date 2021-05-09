package kz.dungeonmasters.core.core_application.data.network

/**
 * Типы вызовов прогресс бара
 */
enum class LoadingType{
    /**
     * Стандатрный
     */
    DEFAULT,

    /**
     * При свайпе
     */
    PULL_TO_REFRESH,

    /**
     * При пагинации
     */
    PAGGING,

    /**
     * Не показываем прогресс бар
     */
    NONE
}