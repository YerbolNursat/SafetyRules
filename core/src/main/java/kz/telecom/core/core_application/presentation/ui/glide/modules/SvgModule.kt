package kz.telecom.core.core_application.presentation.ui.glide.modules

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.PictureDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import com.caverock.androidsvg.SVG
import kz.telecom.core.core_application.presentation.ui.glide.SvgBitmapTranscoder
import kz.telecom.core.core_application.presentation.ui.glide.SvgDecoder
import kz.telecom.core.core_application.presentation.ui.glide.SvgDrawableTranscoder
import java.io.InputStream


@GlideModule
class SvgModule : AppGlideModule() {
    override fun registerComponents(
        context: Context, glide: Glide, registry: Registry
    ) {
        registry
            .register(SVG::class.java, PictureDrawable::class.java, SvgDrawableTranscoder())
            .register(SVG::class.java, Bitmap::class.java, SvgBitmapTranscoder())
            .append(InputStream::class.java, SVG::class.java, SvgDecoder())
    }

    override fun isManifestParsingEnabled(): Boolean {
        return false
    }
}
