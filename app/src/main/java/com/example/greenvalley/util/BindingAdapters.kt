package com.example.greenvalley.util

import android.graphics.drawable.Drawable
import android.os.Build
import android.text.method.LinkMovementMethod
import android.view.View
import android.view.ViewGroup

import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.text.HtmlCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.greenvalley.R

import com.google.android.material.elevation.ElevationOverlayProvider
import com.google.android.material.floatingactionbutton.FloatingActionButton


@BindingAdapter("isGone")
fun bindIsGone(view: View, isGone: Boolean) {
    view.visibility = if (isGone) {
        View.GONE
    } else {
        View.VISIBLE
    }
}



@BindingAdapter("srcUrl", "circleCrop", "placeholder", "loadListener", requireAll = false)
fun ImageView.bindSrcUrl(
        url: String,
        circleCrop: Boolean,
        placeholder: Drawable?,
        loadListener: GlideDrawableLoadListener?
) {
    val request = Glide.with(this).load(url)
    if (circleCrop) request.circleCrop()
    if (placeholder != null) request.placeholder(placeholder)
    if (loadListener != null) request.listener(loadListener)
    request.into(this)
}


@BindingAdapter("imageFromUrl")
fun bindImageFromUrl(view: ImageView, imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()) {
        Glide.with(view.context)
                .load(imageUrl)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(view)
    }
}
@BindingAdapter("isFabGone")
fun bindIsFabGone(view: FloatingActionButton, isGone: Boolean?) {
    if (isGone == null || isGone) {
        view.hide()
    } else {
        view.show()
    }
}
@BindingAdapter("renderHtml")
fun bindRenderHtml(view: TextView, description: String?) {
    if (description != null) {
        view.text = HtmlCompat.fromHtml(description, HtmlCompat.FROM_HTML_MODE_COMPACT)
        view.movementMethod = LinkMovementMethod.getInstance()
    } else {
        view.text = ""
    }
}
@BindingAdapter("wateringText")
fun bindWateringText(textView: TextView, wateringInterval: Int) {
    val resources = textView.context.resources
    val quantityString = resources.getQuantityString(
            R.plurals.watering_needs_suffix,
            wateringInterval,
            wateringInterval
    )

    textView.text = quantityString
}



/**
 * Alter the background color as if this view had the given elevation. We don't want to actually
 * use elevation as the design calls for no shadow.
 */
@BindingAdapter("elevationOverlay")
fun View.bindElevationOverlay(previousElevation: Float, elevation: Float) {
    if (previousElevation == elevation) return
    val color = ElevationOverlayProvider(context)
            .compositeOverlayWithThemeSurfaceColorIfNeeded(elevation)
    setBackgroundColor(color)
}

@BindingAdapter("layoutFullscreen")
fun View.bindLayoutFullscreen(previousFullscreen: Boolean, fullscreen: Boolean) {
    if (previousFullscreen != fullscreen && fullscreen) {
        @Suppress("DEPRECATION")
        // The new alternative is WindowCompat.setDecorFitsSystemWindows, but we can't
        // always get access to the window from a view.
        systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
    }
}

@RequiresApi(Build.VERSION_CODES.R)
@BindingAdapter(
        "paddingLeftSystemWindowInsets",
        "paddingTopSystemWindowInsets",
        "paddingRightSystemWindowInsets",
        "paddingBottomSystemWindowInsets",
        requireAll = false
)
fun View.applySystemWindowInsetsPadding(
        previousApplyLeft: Boolean,
        previousApplyTop: Boolean,
        previousApplyRight: Boolean,
        previousApplyBottom: Boolean,
        applyLeft: Boolean,
        applyTop: Boolean,
        applyRight: Boolean,
        applyBottom: Boolean
) {
    if (previousApplyLeft == applyLeft &&
            previousApplyTop == applyTop &&
            previousApplyRight == applyRight &&
            previousApplyBottom == applyBottom
    ) {
        return
    }

    doOnApplyWindowInsets { view, insets, padding, _ ->
       // val systemBars = insets.getInsets(insets.Type.systemBars())
        val systemBars =insets.getInsets(WindowInsetsCompat.Type.statusBars())
        val left = if (applyLeft) systemBars.left else 0
        val top = if (applyTop) systemBars.top else 0
        val right = if (applyRight) systemBars.right else 0
        val bottom = if (applyBottom) systemBars.bottom else 0

        view.setPadding(
                padding.left + left,
                padding.top + top,
                padding.right + right,
                padding.bottom + bottom
        )
    }
}


@BindingAdapter(
        "marginLeftSystemWindowInsets",
        "marginTopSystemWindowInsets",
        "marginRightSystemWindowInsets",
        "marginBottomSystemWindowInsets",
        requireAll = false
)
fun View.applySystemWindowInsetsMargin(
        previousApplyLeft: Boolean,
        previousApplyTop: Boolean,
        previousApplyRight: Boolean,
        previousApplyBottom: Boolean,
        applyLeft: Boolean,
        applyTop: Boolean,
        applyRight: Boolean,
        applyBottom: Boolean
) {
    if (previousApplyLeft == applyLeft &&
            previousApplyTop == applyTop &&
            previousApplyRight == applyRight &&
            previousApplyBottom == applyBottom
    ) {
        return
    }

    doOnApplyWindowInsets { view, insets, _, margin ->
        val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
        val left = if (applyLeft) systemBars.left else 0
        val top = if (applyTop) systemBars.top else 0
        val right = if (applyRight) systemBars.right else 0
        val bottom = if (applyBottom) systemBars.bottom else 0

        view.updateLayoutParams<ViewGroup.MarginLayoutParams> {
            leftMargin = margin.left + left
            topMargin = margin.top + top
            rightMargin = margin.right + right
            bottomMargin = margin.bottom + bottom
        }
    }
}

fun View.doOnApplyWindowInsets(
        block: (View, WindowInsetsCompat, InitialPadding, InitialMargin) -> Unit
) {
    // Create a snapshot of the view's padding & margin states
    val initialPadding = recordInitialPaddingForView(this)
    val initialMargin = recordInitialMarginForView(this)
    // Set an actual OnApplyWindowInsetsListener which proxies to the given
    // lambda, also passing in the original padding & margin states
    ViewCompat.setOnApplyWindowInsetsListener(this) { v, insets ->
        block(v, insets, initialPadding, initialMargin)
        // Always return the insets, so that children can also use them
        insets
    }
    // request some insets
    requestApplyInsetsWhenAttached()
}




class InitialPadding(val left: Int, val top: Int, val right: Int, val bottom: Int)

class InitialMargin(val left: Int, val top: Int, val right: Int, val bottom: Int)

private fun recordInitialPaddingForView(view: View) = InitialPadding(
        view.paddingLeft, view.paddingTop, view.paddingRight, view.paddingBottom
)

private fun recordInitialMarginForView(view: View): InitialMargin {
    val lp = view.layoutParams as? ViewGroup.MarginLayoutParams
            ?: throw IllegalArgumentException("Invalid view layout params")
    return InitialMargin(lp.leftMargin, lp.topMargin, lp.rightMargin, lp.bottomMargin)
}

fun View.requestApplyInsetsWhenAttached() {
    if (isAttachedToWindow) {
        // We're already attached, just request as normal
        requestApplyInsets()
    } else {
        // We're not attached to the hierarchy, add a listener to
        // request when we are
        addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
            override fun onViewAttachedToWindow(v: View) {
                v.removeOnAttachStateChangeListener(this)
                v.requestApplyInsets()
            }

            override fun onViewDetachedFromWindow(v: View) = Unit
        })
    }
}
