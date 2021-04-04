package kz.dungeonmasters.safetyRules

import androidx.multidex.MultiDexApplication
import kz.dungeonmasters.main.presentation.di.mainModules
import kz.telecom.core.core_application.data.constants.CoreConstant
import kz.telecom.core.core_application.di.coreModule
import kz.telecom.core.core_application.utils.extensions.coreBuilder
import kz.telecom.core.core_application.utils.extensions.initLanguage
import timber.log.Timber

class App : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        setupTimber()
        coreBuilder {
            baseRetrofitUrl { BuildConfig.URL_BASE }
            koinModule { mainModules }
            koinModule { listOf(coreModule) }
            endpointUrlsNecessaryForAuthBearer {
                listOf(CoreConstant.LOGOUT_END_POINT)
            }
            baseHeaderRefreshToken { BuildConfig.BASIC_AUTH_HEADER }
            refreshTokenEndPoint { CoreConstant.REFRESH_TOKEN_END_POINT }
        }
        initLanguage()

    }

    private fun setupTimber() {
        if (BuildConfig.IS_DEBUG) Timber.plant(Timber.DebugTree())
    }

}