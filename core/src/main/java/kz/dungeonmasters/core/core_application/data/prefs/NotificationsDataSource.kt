package kz.dungeonmasters.core.core_application.data.prefs

import android.content.SharedPreferences
import androidx.core.content.edit
import kz.dungeonmasters.core.core_application.data.constants.CoreConstant

class NotificationsDataSource(private val pref: SharedPreferences) {

    fun isNotificationsEnabled() = pref.getBoolean(CoreConstant.PREF_NOTIFICATIONS_ENABLED, true)

    fun setNotificationsEnabled(enabled: Boolean) = pref.edit {
        putBoolean(CoreConstant.PREF_NOTIFICATIONS_ENABLED, enabled)
    }

}