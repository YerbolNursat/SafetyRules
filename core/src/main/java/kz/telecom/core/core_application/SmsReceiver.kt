package kz.telecom.core.core_application

import kz.telecom.core.core_application.utils.event.SingleLiveEvent

class SmsReceiver {
    val sms = SingleLiveEvent<String>()
}