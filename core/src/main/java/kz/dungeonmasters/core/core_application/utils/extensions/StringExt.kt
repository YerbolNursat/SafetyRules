package kz.dungeonmasters.core.core_application.utils.extensions

import android.util.Patterns
import java.net.URLEncoder

fun String.onlyDigits() = replace("[^0-9]".toRegex(), "")

fun String.checkPhoneNumber(): Boolean = length > 9

fun String.isValidEmail() = !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

/**
 * Функция фалидации ИИН
 * @param inn ИИН для проверки
 */
fun String.isValidIIN(inn: String): Boolean {
    val minValue = 12
    if (inn.length < minValue) {
        return false
    }
    var s = 0
    for (i in 0..10) {
        s += (i + 1) * inn[i].toString().toInt()
    }
    var k = s % 11
    if (k == 10) {
        s = 0
        for (i in 0..10) {
            var t = (i + 3) % 11
            if (t == 0) {
                t = 11
            }
            s += t * inn[i].toString().toInt()
        }
        k = s % 11
        if (k == 10) {
            return false
        }
        return (k == inn.substring(11, 12).toInt())
    }
    return (k == inn.substring(11, 12).toInt())
}

/**
 *  Создает param=value string для POST request в WebView
 *  @this - текущий String(field name)
 *  @data - значение для заданного поле(field value)
 */
fun String.inputFormDataField(data: String?): String {
    return "$this=" + URLEncoder.encode(data, Charsets.UTF_8.name())
}