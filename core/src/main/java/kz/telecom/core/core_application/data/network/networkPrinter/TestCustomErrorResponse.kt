package kz.telecom.core.core_application.data.network.networkPrinter

import kz.telecom.core.core_application.utils.parseJson

class TestCustomErrorResponse : NetworkErrorHttpPrinter<TestError> {
    override fun print(response: String?, default: String?) =
        parseJson(response) ?: TestError(helloErrorResponse = default)
}


data class TestError(
    var hello: String? = null,
    var helloErrorResponse: String? = null
)