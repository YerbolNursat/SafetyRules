package kz.dungeonmasters.core.core_application.utils.extensions

import android.app.Application
import kz.dungeonmasters.core.core_application.CoreBuilder
import kz.dungeonmasters.core.core_application.data.constants.CoreConstant
import kz.dungeonmasters.core.lingver.Lingver
import java.util.*

/**
 * Иницальзация core в Application классе
 */
fun Application.coreBuilder(block: CoreBuilder.() -> Unit) = CoreBuilder(this).apply(block).build()

/**
 * Дает возможность использовать язык во всем приложении
 */
fun Application.initLanguage() {
    Lingver.init(this, Locale(CoreConstant.LANG_RU))
}