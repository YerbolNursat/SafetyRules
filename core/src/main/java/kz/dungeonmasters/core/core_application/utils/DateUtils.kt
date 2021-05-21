package kz.dungeonmasters.core.core_application.utils

import android.content.Context
import android.text.format.DateUtils
import kz.dungeonmasters.core.core_application.R
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

private const val RFC3339 = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"

private fun parseDate(date: String): Date? {
    return try {
        val sdf = SimpleDateFormat(RFC3339, Locale.getDefault())
//        sdf.timeZone = TimeZone.getTimeZone("GTM")
        sdf.parse(date)
    } catch (e: Exception) {
        null
    }
}

private fun isYesterday(d: Date): Boolean {
    return DateUtils.isToday(d.time + DateUtils.DAY_IN_MILLIS)
}

private fun isToday(d: Date): Boolean {
    return DateUtils.isToday(d.time)
}

private fun isTomorrow(d: Date): Boolean {
    return DateUtils.isToday(d.time - DateUtils.DAY_IN_MILLIS)
}

fun dateForItemRequest(date: String, context: Context): String? {
    val parsedDate = parseDate(date)
    return parsedDate?.let {
        when {
            isYesterday(parsedDate) -> context.getString(R.string.text_yesterday)
            isToday(parsedDate) -> context.getString(R.string.text_today)
            else -> SimpleDateFormat("dd.MM", Locale.getDefault()).format(it)
        }
    }
}

fun dateForNotification(date: String): String? {
    val parsedDate = parseDate(date)
    return parsedDate?.let { SimpleDateFormat("dd.MM,HH:mm", Locale.getDefault()).format(it) }
}

fun isSameDay(firstDate: String, secondDate: String): Boolean {
    val firstParsedDate = parseDate(firstDate)
    val secondParsedDate = parseDate(secondDate)
    return firstParsedDate == secondParsedDate
}

fun dateForSmsChat(date: String): String? {
    val parsedDate = parseDate(date)
    return parsedDate?.let { SimpleDateFormat("HH:mm", Locale.getDefault()).format(it) }
}

fun dateForChangeLogItem(date: String): String? {
    val parsedDate = parseDate(date)
    return parsedDate?.let { SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(it) }
}
