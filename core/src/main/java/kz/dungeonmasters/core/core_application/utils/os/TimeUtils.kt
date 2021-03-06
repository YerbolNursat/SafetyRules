package kz.dungeonmasters.core.core_application.utils.os

/**
 * Получаем минуты с миллисекунд
 *  @param second
 */
fun convertMillisecondToMinute(time: Long) = time / 1000 / 60

/**
 * Получаем секунды с миллисекунд
 *  @param second
 */
fun convertMillisecondToSecond(time: Long) = (time / 1000 % 60)

/**
 * Получаем с секунд в секунды временного формата
 *  @param second
 */
fun convertSecontToSecond(second: Long) = second % 60

/**
 * Получаем с секунд минуты во врменной формат
 *  @param second
 */
fun convertSecontToMinute(second: Long) = (second % 3600) / 60

/**
 * Получаем с секунд часы во временной формат
 * @param second
 */
fun convertSecontToHour(second: Long) = second / 3600
