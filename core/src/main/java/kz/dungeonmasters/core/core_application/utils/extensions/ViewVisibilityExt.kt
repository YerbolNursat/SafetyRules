package kz.dungeonmasters.core.core_application.utils.extensions

import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.View.GONE

fun View.visible() {
    this.visibility = VISIBLE
}

fun View.invisible() {
    this.visibility = INVISIBLE
}

fun View.gone() {
    this.visibility = GONE
}

fun View.enable() {
    this.isEnabled = true
}

fun View.enable(isEnabled: Boolean) {
    this.isEnabled = isEnabled
}

fun View.disable() {
    this.isEnabled = false
}


