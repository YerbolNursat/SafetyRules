package kz.telecom.core.core_application.data.prefs

import android.content.SharedPreferences
import androidx.core.content.edit
import kz.telecom.core.core_application.data.constants.CoreConstant.EMPTY
import kz.telecom.core.core_application.data.constants.CoreConstant.PREF_ACCESS_PIN_CODE
import kz.telecom.core.core_application.data.constants.CoreConstant.PREF_ACCESS_TEMP_PIN_CODE
import kz.telecom.core.core_application.data.constants.CoreConstant.PREF_AUTH_ACCESS_TOKEN
import kz.telecom.core.core_application.data.constants.CoreConstant.PREF_AUTH_REFRESH_TOKEN
import kz.telecom.core.core_application.data.constants.CoreConstant.PREF_FIREBASE_TOKEN
import kz.telecom.core.core_application.data.constants.CoreConstant.PREF_FIREBASE_TOKEN_SENT
import kz.telecom.core.core_application.data.constants.CoreConstant.PREF_IS_FIRST_LAUNCH
import kz.telecom.core.core_application.data.constants.CoreConstant.PREF_IS_USE_FACE_ID
import kz.telecom.core.core_application.data.constants.CoreConstant.PREF_IS_USE_FINGER_PRINT
import kz.telecom.core.core_application.data.constants.CoreConstant.PREF_NEED_CHRISTMAS_SHEET
import kz.telecom.core.core_application.data.constants.CoreConstant.PREF_PHONE_NUMBER

/**
 * Возможность хранить защишенные данные (токен, пароль, логин)
 */
class SecurityDataSource(private val pref: SharedPreferences) {

    /**
     * Делаем logout пользователя
     */
    fun clearAuthorizedUserData() {
        pref.edit { remove(PREF_ACCESS_PIN_CODE) }
        pref.edit { remove(PREF_AUTH_ACCESS_TOKEN) }
        pref.edit { remove(PREF_AUTH_REFRESH_TOKEN) }
        pref.edit { remove(PREF_FIREBASE_TOKEN) }
        pref.edit { remove(PREF_FIREBASE_TOKEN_SENT) }
    }

    /**
     * Сохранить пин код
     */
    fun setAccessPinCode(pinCode: String) = pref.edit { putString(PREF_ACCESS_PIN_CODE, pinCode) }

    /**
     * Получить пин код
     */
    fun getAccessPinCode() = pref.getString(PREF_ACCESS_PIN_CODE, EMPTY)

    /**
     * Удалить пин код
     */
    fun clearAccessPinCode() = pref.edit { remove(PREF_ACCESS_PIN_CODE) }

    /**
     * Сохранить временный пин код
     */
    fun setAccessTempPinCode(pinCode: String) =
        pref.edit { putString(PREF_ACCESS_TEMP_PIN_CODE, pinCode) }

    /**
     * Получить временный пин код
     */
    fun getAccessTempPinCode() = pref.getString(PREF_ACCESS_TEMP_PIN_CODE, EMPTY)

    /**
     * Удалить временный пин код
     */
    fun cleanTempCode() = pref.edit { remove(PREF_ACCESS_TEMP_PIN_CODE) }

    /**
     * Сохранить auth access token
     */
    fun setAccessToken(token: String) = pref.edit { putString(PREF_AUTH_ACCESS_TOKEN, token) }

    /**
     * Получить auth access token
     */
    fun getAccessToken() = pref.getString(PREF_AUTH_ACCESS_TOKEN, EMPTY)

    /**
     * Сохранить auth refresh token
     */
    fun setRefreshToken(token: String) = pref.edit { putString(PREF_AUTH_REFRESH_TOKEN, token) }

    /**
     * Получить auth refresh token
     */
    fun getRefreshToken() = pref.getString(PREF_AUTH_REFRESH_TOKEN, EMPTY)

    /**
     * Сохранить phoneNumber
     */
    fun setPhoneNumber(phoneNumber: String) =
        pref.edit { putString(PREF_PHONE_NUMBER, phoneNumber) }

    /**
     * Получить phoneNumber
     */
    fun getPhoneNumber() = pref.getString(PREF_PHONE_NUMBER, EMPTY)


    /**
     * Задаем если необходимо использовать fingerPrint
     */
    fun setUseFingerPrint(isUseFingerPrint: Boolean) =
        pref.edit { putBoolean(PREF_IS_USE_FINGER_PRINT, isUseFingerPrint) }

    /**
     * Задаем если необходимо использовать faceId
     */
    fun setUseFaceId(isUseFingerPrint: Boolean) =
        pref.edit { putBoolean(PREF_IS_USE_FACE_ID, isUseFingerPrint) }

    /**
     * Проверка используеться ли отпечаток пальца
     */
    fun isUseFingerPrint() =
        pref.getBoolean(PREF_IS_USE_FINGER_PRINT, false)

    /**
     * Проверка используеться ли FaceId
     */
    fun isUseFaceIdPrint() = pref.getBoolean(PREF_IS_USE_FACE_ID, false)

    /**
     * Очищаем fingerPrint
     */
    fun clearFingerPrint() = pref.edit { remove(PREF_IS_USE_FINGER_PRINT) }


    /**
     * Очищаем faceId
     */
    fun clearFaceId() = pref.edit { remove(PREF_IS_USE_FACE_ID) }

    fun getFirebaseToken(): String? {
        return pref.getString(PREF_FIREBASE_TOKEN, null)
    }

    fun setFirebaseToken(token: String) {
        pref.edit {
            putString(PREF_FIREBASE_TOKEN, token)
        }
    }

    fun isFirebaseTokenSent(): Boolean {
        return pref.getBoolean(PREF_FIREBASE_TOKEN_SENT, false)
    }

    fun setFirebaseTokenSent(sent: Boolean) {
        pref.edit {
            putBoolean(PREF_FIREBASE_TOKEN_SENT, sent)
        }
    }

    fun getIsFirstLaunch(): Boolean {
        return pref.getBoolean(PREF_IS_FIRST_LAUNCH, true)
    }

    fun setIsFirstLaunch(status: Boolean) {
        pref.edit {
            putBoolean(PREF_IS_FIRST_LAUNCH, status)
        }
    }


}