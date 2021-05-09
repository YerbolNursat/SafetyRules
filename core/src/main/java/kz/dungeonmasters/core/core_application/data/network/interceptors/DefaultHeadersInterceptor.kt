package kz.dungeonmasters.core.core_application.data.network.interceptors

import android.content.Context
import kz.dungeonmasters.core.core_application.utils.delegates.LocaleDelegate
import okhttp3.Interceptor
import okhttp3.Response
import java.util.*

class DefaultHeadersInterceptor(
    var context: Context
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val locale = LocaleDelegate(context).getLocale()
        val request = chain.request().newBuilder()
            .header("Content-Type", "application/json")
            .header("Accept-Language", locale)
            .build()

        return chain.proceed(request)
    }

}