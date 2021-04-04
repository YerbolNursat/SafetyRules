package kz.telecom.core.core_application

import android.app.Activity
import android.app.Application
import kz.telecom.core.core_application.data.constants.CoreVariables.APOLLO_REFRESH_TOKEN_INTERCEPTOR
import kz.telecom.core.core_application.data.constants.CoreVariables.BASE_APOLLO_URL
import kz.telecom.core.core_application.data.constants.CoreVariables.BASE_URL
import kz.telecom.core.core_application.data.constants.CoreVariables.BASIC_REFRESH_AUTH_HEADER
import kz.telecom.core.core_application.data.constants.CoreVariables.IMAGE_URL
import kz.telecom.core.core_application.data.constants.CoreVariables.IS_PRODUCTION
import kz.telecom.core.core_application.data.constants.CoreVariables.LOGIN_ACTIVITY
import kz.telecom.core.core_application.data.constants.CoreVariables.OPERATOR
import kz.telecom.core.core_application.data.constants.CoreVariables.REFRESH_TOKEN_END_POINT
import kz.telecom.core.core_application.data.constants.CoreVariables.URLS_OF_UNNECESSARY_BEARER_TOKEN_ENDPOINTS
import okhttp3.Interceptor
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.module.Module


class CoreBuilder(private val application: Application) {

    private var koinModule = mutableListOf<Module>()

    /**
     * Базовый url для retrofit
     * @param {String.()} строка с url для apollo
     */
    fun baseRetrofitUrl(block: () -> String) {
        BASE_URL = block()
    }

    /**
     * Базовый url для изображений
     * @param {String.()} строка с url для изображений
     */
    fun baseImageUrl(block: () -> String) {
        IMAGE_URL = block()
    }

    /**
     * Базовые url для клиента Apollo
     * @param {String.()} строка с url для apollo
     */
    fun baseApolloUrl(block: () -> String) {
        BASE_APOLLO_URL = block()
    }

    /**
     * Модуля для koin
     * @param {List.()} список с модулями
     */
    fun koinModule(block: () -> List<Module>) {
        koinModule.addAll(block())
    }

    /**
     * Список url которым не требуються header-ы
     * @param {List.()} список с url
     */
    fun endpointUrlsNecessaryForAuthBearer(block: () -> List<String>) {
        URLS_OF_UNNECESSARY_BEARER_TOKEN_ENDPOINTS = block()
    }

    /**
     * Базовый header для refresh token
     * @param {String.()} строка с базовым header-ом
     */
    fun baseHeaderRefreshToken(block: () -> String) {
        BASIC_REFRESH_AUTH_HEADER = block()
    }

    /**
     * передаем запрос для refresh token например "api/refresh/token"
     * @param {String.()} строка с end point
     */
    fun refreshTokenEndPoint(block: () -> String) {
        REFRESH_TOKEN_END_POINT = block()
    }


    /**
     * При испечении токена перенаправляем на данное activity
     * @param {Activity.()} объект activity
     */
    fun loginActivity(block: () -> Activity) {
        LOGIN_ACTIVITY = block()
    }

    /**
     * Передаем оператор мобильной связи, например TELE2, ALTEL
     * @param {String.()} передаем стринг
     */
    fun operator(block: () -> String?) {
        OPERATOR = block().orEmpty()
    }

    /**
     * Передаем флаг true если отправляем на демонстарцию или продакшен
     * Для разработки передаем false
     * Для корректной работы приложениея передаем true
     */
    fun isProdaction(block: () -> Boolean) {
        IS_PRODUCTION = block()
    }

    /**
     * Передаем интерцептор для обновления токена для apollo клиента,
     * так как реализация на каждо сервере отличаеться
     *
     */
    fun apolloHeaderInterceptor(block: () -> Interceptor?) {
        APOLLO_REFRESH_TOKEN_INTERCEPTOR = block()
    }


    fun build() {
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@CoreBuilder.application.applicationContext)
            modules(koinModule)
        }
    }
}