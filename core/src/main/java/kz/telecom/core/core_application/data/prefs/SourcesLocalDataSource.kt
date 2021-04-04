package kz.telecom.core.core_application.data.prefs

import android.content.SharedPreferences
import androidx.core.content.edit
import kz.telecom.core.core_application.data.constants.CoreConstant
import kz.telecom.core.core_application.data.constants.CoreConstant.PREF_SESSION_TIME
import kz.telecom.core.core_application.data.constants.CoreConstant.PREF_THEME
import kz.telecom.core.core_application.utils.delegates.Theme


class SourcesLocalDataSource(private val pref: SharedPreferences) {

    /**
     *  задать тему
     */
    fun setTheme(theme: Theme) = pref.edit {
        putString(PREF_THEME, theme.name)
    }

    /**
     * Получаем тему
     */
    fun getTheme(defaultValue: String = CoreConstant.EMPTY) = pref.getString(PREF_THEME, defaultValue)

    /**
     * Записываем последнее время использования приложения
     */
    fun setLastTimeUseApplication(time: Long) = pref.edit {
        putLong(PREF_SESSION_TIME, time)
    }

    /**
     * Получаем последнее время использования приложения
     */
    fun getLastTimeUseApplication() = pref.getLong(PREF_SESSION_TIME, 0L)

    /**
     * Удаляем последнее время использования приложения
     */
    fun removeLastTimeUseApplication() = pref.edit { remove(PREF_SESSION_TIME) }

}
