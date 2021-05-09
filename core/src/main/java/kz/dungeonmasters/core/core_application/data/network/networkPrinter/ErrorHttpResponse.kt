package kz.dungeonmasters.core.core_application.data.network.networkPrinter

import kz.dungeonmasters.core.core_application.data.constants.CoreConstant
import kz.dungeonmasters.core.core_application.utils.parseJson

class ErrorHttpResponse : NetworkErrorHttpPrinter<String> {
    override fun print(response: String?, default: String?): String {
        return parseJson<DefaultError>(response)?.getErrorMessage()  ?: default ?: CoreConstant.EMPTY
    }
}

class DefaultError(val error: String? = null, private val message: String? = null){
    fun getErrorMessage() = message ?: error ?: CoreConstant.EMPTY
}


