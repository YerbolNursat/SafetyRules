package kz.dungeonmasters.core.core_application.data.network

interface Logout {

    fun logout(customerId: Int?, authToken: String?)
}