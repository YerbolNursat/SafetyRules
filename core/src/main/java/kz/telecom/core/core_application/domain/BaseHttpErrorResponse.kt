package kz.telecom.core.core_application.domain

import kz.telecom.core.core_application.data.network.networkPrinter.NetworkErrorHttpPrinter
import kz.telecom.core.core_application.utils.parseJson


class BaseHttpErrorResponse :
    NetworkErrorHttpPrinter<BaseError> {
    override fun print(response: String?, default: String?) =
        parseJson<BaseError>(response) ?: BaseError(
            message = default
        )
}

data class BaseError(
    var code: String? = null,
    var message: String? = null
)
