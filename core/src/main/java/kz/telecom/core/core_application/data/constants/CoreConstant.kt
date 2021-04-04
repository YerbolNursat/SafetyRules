package kz.telecom.core.core_application.data.constants

object CoreConstant{
    const val EMPTY = ""

    const val CONNECTION_TIMEOUT = 60000L
    const val READ_TIMEOUT = 60000L
    const val PERMISSION_DENIED = -1

    const val PREF_ACCESS_TEMP_PIN_CODE = "PREF_ACCESS_TEMP_PIN_CODE"
    const val PREF_ACCESS_PIN_CODE = "PREF_ACCESS_PIN_CODE"
    const val PREF_AUTH_ACCESS_TOKEN = "PREF_AUTH_ACCESS_TOKEN"
    const val PREF_AUTH_REFRESH_TOKEN = "PREF_AUTH_REFRESH_TOKEN"
    const val PREF_THEME = "PREF_THEME"
    const val  PREF_CURRENT_LOCALE = "PREF_CURRENT_LOCALE"
    const val PREF_NOTIFICATIONS_ENABLED = "PREF_NOTIFICATIONS_ENABLED"
    const val PREF_SESSION_TIME = "PREF_SESSION_TIME"
    const val PREF_SECRET_SHARED = "SECRET_SHARED_PREF"
    const val PREF_IS_USE_FINGER_PRINT = "PREF_IS_USE_FINGER_PRINT"
    const val PREF_IS_USE_FACE_ID = "PREF_IS_USE_FACE_ID"
    const val PREF_IS_FIRST_LAUNCH = "PREF_IS_FIRST_LAUNCH"
    const val PREF_FIREBASE_TOKEN = "PREF_FIREBASE_TOKEN"
    const val PREF_FIREBASE_TOKEN_SENT = "PREF_FIREBASE_TOKEN_SENT"
    const val PREF_NEED_CHRISTMAS_SHEET = "PREF_NEED_CHRISTMAS_SHEET"

    const val CHANNEL_NAME = "NOTIFICATION"
    const val CHANNEL_DESC = "Firebase Cloud Messaging"

    const val PREF_PHONE_NUMBER = "PREF_PHONE_NUMBER"

    const val ARG_REDIRECT = "ARG_REDIRECT"
    const val ARG_ANIM = "ARG_ANIM"
    const val ARG_ACTION = "ARG_ACTION"

    const val SESSION_END = "SESSION_END"

    const val AUTHORIZATION  = "Authorization"
    const val GRANT_TYPE_REFRESH_TOKEN = "refresh_token"
    const val BEARER = "Bearer"

    const val MIN_VERSION_SUPPORT_DARK_MODE = 10

    const val REFRESH_TOKEN_END_POINT = "auth/refresh"
    const val LOGOUT_END_POINT = "auth/logout"

    const val MAIN_ACTIVITY = "kz.telecom.main.presentation.ui.MainActivity"
    const val AUTH_ACTIVITY = "kz.telecom.auth.presentation.ui.AuthActivity"

    const val LANG_RU = "ru"
    const val LANG_KZ = "kz"

    const val MAP_API = "7a9d4c95-ab1e-4805-8917-811ecf32451b"

}