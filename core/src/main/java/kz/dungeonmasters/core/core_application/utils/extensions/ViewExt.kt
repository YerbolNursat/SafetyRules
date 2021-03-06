package kz.dungeonmasters.core.core_application.utils.extensions

import android.animation.Animator
import android.animation.ArgbEvaluator
import android.animation.StateListAnimator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.PictureDrawable
import android.net.Uri
import android.os.Build
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.*
import androidx.annotation.ColorInt
import androidx.annotation.RequiresApi
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.widget.NestedScrollView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.material.tabs.TabLayout
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kz.dungeonmasters.core.core_application.R
import kz.dungeonmasters.core.core_application.data.constants.CorePatternConstant
import kz.dungeonmasters.core.core_application.data.constants.FileConstant.SVG_FILE
import kz.dungeonmasters.core.core_application.presentation.model.UIDrawableClick
import kz.dungeonmasters.core.core_application.presentation.ui.glide.ImgSoftwareLayer
import kz.dungeonmasters.core.core_application.presentation.ui.glide.SvgSoftwareLayerSetter
import kz.dungeonmasters.core.core_application.presentation.ui.widget.editText.textWatcher.PhoneNumberTextWatcher
import kz.dungeonmasters.core.core_application.presentation.ui.widget.editText.textWatcher.amount.AmountWithZeroTextWatcher
import kz.dungeonmasters.core.core_application.utils.os.getExtension
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

/**
 * Created by Yergali Zhakhan on 5/20/20.
 */

@SuppressLint("RestrictedApi")
fun ImageView.colorAnimator(
    @ColorInt from: Int,
    @ColorInt to: Int,
    durationInMillis: Long
): Animator = ValueAnimator.ofObject(ArgbEvaluator(), from, to).apply {
    duration = durationInMillis
    addUpdateListener { animator ->
        val color = animator.animatedValue as Int
        run { setColorFilter(color) }
    }
}

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
fun ImageView.setColorStateListAnimator(
    @ColorInt color: Int,
    @ColorInt unselectedColor: Int
) {
    val stateList = StateListAnimator().apply {
        addState(
            intArrayOf(android.R.attr.state_selected),
            colorAnimator(unselectedColor, color, 350)
        )
        addState(
            intArrayOf(),
            colorAnimator(color, unselectedColor, 350)
        )
    }

    stateListAnimator = stateList

    // Refresh the drawable state to avoid the unselected animation on view creation
    refreshDrawableState()
}

var DURATION = 150L
var ALPHA = 1.0f
fun TextView.expand(container: LinearLayout, iconColor: Int, itemWidth: Int) {
    val bounds = Rect()
    val bubbleWidth = if (itemWidth > 0) itemWidth else bounds.width() + paddingLeft + 10
    container.setCustomBackground(iconColor, ALPHA, true)
    paint.apply {

        getTextBounds(text.toString(), 0, text.length, bounds)
        ValueAnimator.ofInt(0, bubbleWidth).apply {
            addUpdateListener {
                if (it.animatedFraction == (0.0f)) {
                    visibility = View.INVISIBLE
                }
                layoutParams.apply {
                    width = it.animatedValue as Int
                }

                if (it.animatedFraction == (1.0f)) {
                    visibility = View.VISIBLE
                }

                requestLayout()
            }
            interpolator = LinearInterpolator()

            duration = DURATION
        }.start()
    }
}


fun TextView.collapse(
    container: LinearLayout,
    iconColor: Int
) {
    animate().alpha(0f).apply {
        setUpdateListener {
            layoutParams.apply {
                width = (width - (width * it.animatedFraction)).toInt()
            }
            if (it.animatedFraction == 1.0f) {
                visibility = View.GONE
                alpha = 1.0f
            }
            interpolator = LinearInterpolator()
            duration = DURATION
            container.setCustomBackground(iconColor, ALPHA - (ALPHA * it.animatedFraction), false)
            requestLayout()
        }
    }.start()

}

fun View.setCustomBackground(color: Int, alpha: Float, isExpand: Boolean) {
    val containerBackground = GradientDrawable().apply {
        cornerRadius = 100f
        DrawableCompat.setTint(
            this,
            Color.argb(
                (Color.alpha(color) * alpha).toInt(),
                Color.red(color),
                Color.green(color),
                Color.blue(color)
            )
        )
    }
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
        containerBackground.setColor(if (isExpand) color else Color.TRANSPARENT)
    }
    background = containerBackground
}


/**
 * ?????????????????? ???????????????????? ????????
 */
fun TextView.doOnTextChange(block: (CharSequence?) -> Any) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {
            // do nothing
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            // do nothing
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            block(p0)
        }

    })
}


/**
 * ?????????????????? ???????????????????? ???????? ?? ?????????????????????????? ?????????????????? ????????????????
 */
fun EditText.amountWithZeroDoOnTextChange(
    patten: String = CorePatternConstant.PATTERN_FORMAT_ANOUNT_SPACE_ZERO,
    block: (String) -> Unit
) {
    this.addTextChangedListener(
        AmountWithZeroTextWatcher(
            patten,
            this
        ) {
            block(it)
        })
}


/**
 * ?????????????????? ???????????????????? ???????? ?? ?????????????????????????? ?????????????????? ????????????????
 */
fun EditText.phoneNumberDoOnTextChange(
    block: (String) -> Unit,
    mask: String = CorePatternConstant.PATTERN_DEFAULT_PHONE_NUMBER
) {
    this.addTextChangedListener(PhoneNumberTextWatcher(this, mask) {
        block(it)
    })
}

/**
 * ?????????????????????????? ??????????/???????????????? view
 * @param value View.GONE - ???????????? View.VISIBLE - ????????????????
 */
fun View.animationVisibility(value: Int) {
    val valueAnimator = when (value) {
        View.VISIBLE -> {
            ValueAnimator.ofFloat(0f, 1f).apply {
                doOnStart {
                    this@animationVisibility.visibility = View.VISIBLE
                }
            }
        }
        View.GONE -> {
            ValueAnimator.ofFloat(1f, 0f).apply {
                doOnEnd {
                    this@animationVisibility.visibility = View.GONE
                }
            }
        }
        else -> null
    }

    valueAnimator?.addUpdateListener {
        this.alpha = it.animatedValue as Float
    }

    val isRunning = valueAnimator?.isRunning ?: true
    if (!isRunning)
        valueAnimator?.start()
}

/**
 * ?????????????????? ?????????????? viewPager
 */
fun ViewPager.scrollListener(block: (Int) -> Unit) {
    addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
        override fun onPageScrollStateChanged(state: Int) {}
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
        }

        override fun onPageSelected(position: Int) {
            block(position)
        }
    })
}


/**
 * ?????????????????? ?????? TabLayout
 * @return block ?????????????????? ?????????????????? ??????
 */
fun TabLayout.selectedListener(block: ((TabLayout.Tab?) -> Unit)) {
    addOnTabSelectedListener(object :
        TabLayout.OnTabSelectedListener {
        override fun onTabReselected(tab: TabLayout.Tab?) {
        }

        override fun onTabUnselected(tab: TabLayout.Tab?) {
        }

        override fun onTabSelected(tab: TabLayout.Tab?) {
            block(tab)
        }
    })
}

/**
 * ?????????????????????? ?????????????? ???? ???????????? ?????????? ?? navigation ?????????????? ????????????????
 */
fun View.onKeyBackPressed(block: () -> Unit) {
    this.isFocusableInTouchMode = true
    this.requestFocus()
    this.setOnKeyListener(object : View.OnKeyListener {
        override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
            return if (keyCode == KeyEvent.KEYCODE_BACK) {
                block()
                true
            } else {
                false
            }
        }
    })
}


/**
 * ?????????????? ???? view ?????????????????????? ???? ???????? ?????? ???????????????????? ??????????????????
 */
fun ImageView.loadImage(url: Any) {

    val isSvg = getExtension(url.toString()) == SVG_FILE
    val request = if (isSvg) {
        Glide.with(this.context).`as`(PictureDrawable::class.java)
            .transition(withCrossFade())
            .listener(SvgSoftwareLayerSetter())
            .load(Uri.parse(url.toString()))
    } else {
        Glide.with(this.context)
            .load(url)

    }
    request.centerCrop().into(this)
}


/**
 * Load image with cache
 */
fun ImageView.loadImageWithCache(url: Any) {
    val isSvg = getExtension(url.toString()) == SVG_FILE
    val request = if (isSvg) {
        Glide.with(this.context).`as`(PictureDrawable::class.java)
            .transition(withCrossFade())
            .listener(SvgSoftwareLayerSetter())
            .load(Uri.parse(url.toString()))
    } else {
        Glide.with(this.context).load(url)

    }
    request.diskCacheStrategy(DiskCacheStrategy.RESOURCE).into(this)
}

/**
 * ?????????????? ???? view ?????? ?????????? ?????????????????????? ???? ???????? ?????? ???????????????????? ??????????????????
 */
fun ImageView.loadImageWithoutCrop(url: Any) {
    val isSvg = getExtension(url.toString()) == SVG_FILE
    val request = if (isSvg) {
        Glide.with(this.context).`as`(PictureDrawable::class.java)
            .transition(withCrossFade())
            .listener(SvgSoftwareLayerSetter())
            .load(Uri.parse(url.toString()))
    } else {
        Glide.with(this.context)
            .load(url)
    }
    request.into(this)
}

/**
 * ???????????????? drawable ???? ?????????????????????? ???? url
 * @param path ???????? ???? ??????????????????????
 * @param block ???????????? ?????????????? ?????????????????? Drawable
 *
 */
fun Context.getDrawableUrlImage(path: Any, block: (Drawable) -> Unit) {

    Glide.with(this).`as`(Bitmap::class.java)
        .load(path)
        .into(object : CustomTarget<Bitmap>() {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                block(BitmapDrawable(resources, resource))
            }

            override fun onLoadCleared(placeholder: Drawable?) {
            }
        })


}

/**
 * Load image with cache and success/fail listeners
 */
fun ImageView.loadImageWitCacheAndListener(
    url: Any,
    readyBlock: (Drawable?) -> Unit,
    failBlock: (Boolean) -> Unit
) {
    val isSvg = getExtension(url.toString()) == SVG_FILE
    val request = if (isSvg) {
        Glide.with(this.context).`as`(PictureDrawable::class.java)
            .transition(withCrossFade())
            .listener(SvgSoftwareLayerSetter(readyBlock, failBlock))
            .load(Uri.parse(url.toString()))
    } else {
        Glide.with(this.context)
            .load(url)
            .listener(ImgSoftwareLayer(readyBlock, failBlock))

    }
    request.diskCacheStrategy(DiskCacheStrategy.RESOURCE).into(this)
}

/**
 * ???????????? view ????????????????????
 * @param isViewEnabled true view ???????????????? false ????????????????????
 */
fun View.setStateEnable(isViewEnabled: Boolean) {
    this.isClickable = isViewEnabled
    this.isEnabled = isViewEnabled
    this.alpha = if (isViewEnabled) 1.0f else 0.4f
}

/**
 * ?????????????????? ???????????? ?????? ????????????
 */
fun TextView.drawableClickListener(drawablePosition: UIDrawableClick, block: (() -> Unit)) {

    val padding = when (drawablePosition) {
        UIDrawableClick.TOP -> totalPaddingTop
        UIDrawableClick.LEFT -> totalPaddingStart
        UIDrawableClick.RIGHT -> totalPaddingEnd
        UIDrawableClick.BOTTOM -> totalPaddingBottom

    }

    setOnTouchListener { v, event ->
        if (event.action == MotionEvent.ACTION_UP) {
            if (event.rawX >= right - padding) {
                block()
            } else {
                requestFocus()
                context.showKeyboard(this)
            }
        }
        true
    }
}

/**
 * ???????????? ???????????????????????? ???????????? ???????????????? ???????????????????? ????????
 * @param value ????????????????
 */
fun EditText.setMaxLength(value: Int) {
    val fArray = arrayOfNulls<InputFilter>(1)
    fArray[0] = InputFilter.LengthFilter(value)
    filters = fArray
}


/**
 * ???????????????????????? TextView
 */
fun TextView.underline() {
    this.paintFlags = this.paintFlags or Paint.UNDERLINE_TEXT_FLAG
}


/**
 * ?????????????????? NestedScrollView ?????????????????????? ?????????? ?????????? ?????????????? ???? ?????????? ????????????????
 */
fun NestedScrollView.doOnEndPage(block: () -> Unit) {
    this.setOnScrollChangeListener { v: NestedScrollView, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int ->
        val view = v.getChildAt(v.childCount - 1) as View
        val diff: Int = view.bottom - (v.height + v.scrollY)
        if (diff == 0) {
            block()
        }
    }
}


@ExperimentalCoroutinesApi
fun TextView.textClicks() = callbackFlow {
    setOnClickListener { offer(text.toString()) }
    awaitClose { setOnClickListener(null) }
}

@SuppressLint("SetTextI18n")
@BindingAdapter("setMoneyFormatted")
fun TextView.setMoneyFormatted(currency: Double) {
    val format = NumberFormat.getCurrencyInstance(Locale("kk", "KZ"))
    text = format.format(currency)
}

@SuppressLint("SetTextI18n")
@BindingAdapter("setMoneyFormattedCustom")
fun TextView.setMoneyFormattedCustom(currency: Int) {
    val format: NumberFormat = DecimalFormat("#,###")
    text = "${format.format(currency).replace(",", " ")} ${context.getString(R.string.text_amount_of_money_in_month)}"
}

@SuppressLint("SetTextI18n")
@BindingAdapter("setMoneyFormattedCustomWithFrom")
fun TextView.setMoneyFormattedCustomWithFrom(currency: Int) {
    val format: NumberFormat = DecimalFormat("#,###")
    text = "${context.getString(R.string.text_from)} ${format.format(currency).replace(",", " ")} ${context.getString(R.string.text_amount_of_money_in_month)}"
}

fun ViewPager2.ignorePullToRefresh(swipeRefreshLayout: SwipeRefreshLayout) {
    this.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
        override fun onPageScrollStateChanged(state: Int) {
            super.onPageScrollStateChanged(state)
            if (!swipeRefreshLayout.isRefreshing) {
                swipeRefreshLayout.isEnabled = state == ViewPager2.SCROLL_STATE_IDLE
            }
        }
    })
}

@BindingAdapter("previewVideo")
fun VideoView.previewVideo(url: String) {
    setVideoPath(url)
    seekTo(100)

}