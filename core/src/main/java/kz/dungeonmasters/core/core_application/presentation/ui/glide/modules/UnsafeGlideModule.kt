package kz.dungeonmasters.core.core_application.presentation.ui.glide.modules

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.LibraryGlideModule
import kz.dungeonmasters.core.core_application.BuildConfig
import kz.dungeonmasters.core.core_application.utils.network.getSsl
import okhttp3.OkHttpClient
import java.io.InputStream
import javax.net.ssl.HostnameVerifier


@GlideModule
class UnsafeGlideModule : LibraryGlideModule() {
    override fun registerComponents(
        context: Context, glide: Glide, registry: Registry
    ) {
        if (BuildConfig.DEBUG) {
            val (manager, factory) = getSsl()
            val okHttpBuilder = OkHttpClient.Builder()
            okHttpBuilder.sslSocketFactory(factory, manager)
            okHttpBuilder.hostnameVerifier(HostnameVerifier { _, _ -> true })
            registry.replace(GlideUrl::class.java, InputStream::class.java, OkHttpUrlLoader.Factory(okHttpBuilder.build()))
        }
    }
}
