package kz.dungeonmasters.core.core_application.di

import android.content.Context
import com.apollographql.apollo.ApolloClient
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.securepreferences.SecurePreferences
import kz.dungeonmasters.core.core_application.BuildConfig
import kz.dungeonmasters.core.core_application.SmsReceiver
import kz.dungeonmasters.core.core_application.data.constants.CoreConstant
import kz.dungeonmasters.core.core_application.data.constants.CoreVariables
import kz.dungeonmasters.core.core_application.data.constants.CoreVariables.PREF_SOURCES_LOCAL
import kz.dungeonmasters.core.core_application.data.network.interceptors.DefaultHeadersInterceptor
import kz.dungeonmasters.core.core_application.data.network.interceptors.OAuthInterceptor
import kz.dungeonmasters.core.core_application.data.prefs.*
import kz.dungeonmasters.core.core_application.presentation.ui.activities.CurrentActivityHolder
import kz.dungeonmasters.core.core_application.utils.network.getSsl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier

val coreModule = module {
    factory { createRetrofitOkHttpClient(androidContext()) }
    single { SecurityDataSource(SecurePreferences(androidContext())) }
    single { NotificationsDataSource(SecurePreferences(androidContext())) }
    single {
        SourcesLocalDataSource(
            androidContext().getSharedPreferences(
                PREF_SOURCES_LOCAL,
                Context.MODE_PRIVATE
            )
        )
    }
    single { SharedPrefLayer(androidContext()) }
    single { createApollo(createApolloOkHttpClient(androidContext())) }
    single { SmsReceiver() }
    single {
        LanguageDataSource(
            androidContext().getSharedPreferences(
                PREF_SOURCES_LOCAL,
                Context.MODE_PRIVATE
            )
        )
    }
    single { CurrentActivityHolder(null) }
}

/**
 * OkHttpClient использовать только для retrofit
 */
fun createRetrofitOkHttpClient(context: Context): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)
    val okHttpBuilder = OkHttpClient.Builder()
        .addNetworkInterceptor(DefaultHeadersInterceptor(context))
        .addInterceptor(OAuthInterceptor())
        .addInterceptor(httpLoggingInterceptor)
        .connectTimeout(CoreConstant.CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)
        .readTimeout(CoreConstant.READ_TIMEOUT, TimeUnit.MILLISECONDS)
    if (BuildConfig.DEBUG) {
        val (manager, factory) = getSsl()
        okHttpBuilder.sslSocketFactory(factory, manager)
        okHttpBuilder.hostnameVerifier(HostnameVerifier { _, _ -> true })
    }
    return okHttpBuilder.build()
}

/**
 * OkHttpClient использовать только для Apollo
 */
fun createApolloOkHttpClient(context: Context): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)
    val okHttpBuilder = OkHttpClient.Builder()
        .addInterceptor(DefaultHeadersInterceptor(context))
        .addInterceptor(CoreVariables.APOLLO_REFRESH_TOKEN_INTERCEPTOR ?: return OkHttpClient())
        .addInterceptor(httpLoggingInterceptor)
        .connectTimeout(CoreConstant.CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)
        .readTimeout(CoreConstant.READ_TIMEOUT, TimeUnit.MILLISECONDS)
    if (BuildConfig.DEBUG) {
        val (manager, factory) = getSsl()
        okHttpBuilder.sslSocketFactory(factory, manager)
        okHttpBuilder.hostnameVerifier(HostnameVerifier { _, _ -> true })
    }
    return okHttpBuilder.build()
}

/**
 * Создание api сервисов для Retrofit
 */
inline fun <reified T> createWebService(
    okHttpClient: OkHttpClient,
    baseUrl: String = CoreVariables.BASE_URL
): T = Retrofit.Builder()
    .baseUrl(baseUrl)
    .client(okHttpClient)
    .addConverterFactory(GsonConverterFactory.create(createGson()))
    .build()
    .create(T::class.java)

fun createGson(): Gson = GsonBuilder().setLenient().create()

/**
 * Создание сервиса для Apollo
 */
fun createApollo(okHttp: OkHttpClient): ApolloClient =
    ApolloClient.builder().serverUrl(CoreVariables.BASE_APOLLO_URL).okHttpClient(okHttp).build()






