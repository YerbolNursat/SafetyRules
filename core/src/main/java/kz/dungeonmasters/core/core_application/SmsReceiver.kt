package kz.dungeonmasters.core.core_application

import kz.dungeonmasters.core.core_application.utils.event.SingleLiveEvent

class SmsReceiver {
    val sms = SingleLiveEvent<String>()
}