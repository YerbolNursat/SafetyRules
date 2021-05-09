package kz.dungeonmasters.core.core_application.presentation.ui.glide

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.PictureDrawable
import com.bumptech.glide.load.Options
import com.bumptech.glide.load.engine.Resource
import com.bumptech.glide.load.resource.SimpleResource
import com.bumptech.glide.load.resource.transcode.ResourceTranscoder
import com.caverock.androidsvg.SVG

/**
 * Тринсформируем из svg в PictureDrawable
 */
class SvgDrawableTranscoder : ResourceTranscoder<SVG?, PictureDrawable?> {

    override fun transcode(
        toTranscode: Resource<SVG?>,
        options: Options
    ): Resource<PictureDrawable?>? {
        val svg: SVG = toTranscode.get()
        val picture = svg.renderToPicture()
        val drawable = PictureDrawable(picture)
        return SimpleResource(drawable)
    }
}

/**
 * Тринсформируем из svg в Bitmap
 */
class SvgBitmapTranscoder : ResourceTranscoder<SVG?, Bitmap?> {

    override fun transcode(
        toTranscode: Resource<SVG?>,
        options: Options
    ): Resource<Bitmap?>? {
        val svg: SVG = toTranscode.get()
        val picture = svg.renderToPicture()
        val drawable = PictureDrawable(picture)
        val bitmap = Bitmap.createBitmap(
            drawable.intrinsicWidth,
            drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        canvas.drawPicture(drawable.picture)
        return SimpleResource(bitmap)
    }
}
