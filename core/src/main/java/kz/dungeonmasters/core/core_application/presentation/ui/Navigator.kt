package kz.dungeonmasters.core.core_application.presentation.ui

interface Navigator {
    fun toHome()
    fun toHomeWithPay()
    fun toRequests(status: Int = 0)
    fun toDetails()
    fun toDetailsAndPayments()
    fun toProfile()
}