package com.example.greenvalley.ui.filterItems

import android.animation.ValueAnimator
import android.graphics.*
import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import androidx.annotation.Px
import androidx.core.graphics.toRectF
import androidx.core.graphics.withScale
import androidx.interpolator.view.animation.FastOutSlowInInterpolator

/**
 * A [Drawable] that responds to `state_activated' by
 * - Drawing a tint overlay
 * - Rounding it's top left corner radius
 * - Overlaying another drawable showing the selection
 */
private const val ANIM_DURATION = 300L
class ItemThumbnailDrawable(
        @ColorInt selectedTint: Int,
        @Px private val selectedTopLeftCornerRadius: Int,
        private val selectedMark: Drawable

) :Drawable(){

    private val thumbPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val tintPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = selectedTint
        style = Paint.Style.FILL
    }

    var bitmap: Bitmap? = null
        set(value) {
            if (value != null) {
                thumbPaint.shader = BitmapShader(value, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
            }
            progress = 0f
        }

    private val tintAlpha = Color.alpha(selectedTint)
    private val path = Path()
    private var progress = 0f
        set(value) {
            val clamped = value.coerceIn(0f, 1f)
            if (clamped != field) {
                field = clamped
                update()
            }
        }

    private var progressAnim: ValueAnimator? = null
    private val interp = FastOutSlowInInterpolator()
    private var pivotX = 0f
    private var pivotY = 0f

    private fun update() {
        path.run {
            if (!bounds.isEmpty) {
                reset()
                val topLeftRadius = selectedTopLeftCornerRadius * progress
                addRoundRect(
                        bounds.toRectF(),
                        floatArrayOf(
                                topLeftRadius, topLeftRadius, 0f, 0f, 0f, 0f, 0f, 0f
                        ),
                        Path.Direction.CW
                )
            }
        }
        tintPaint.alpha = (progress * tintAlpha).toInt()
        callback?.invalidateDrawable(this)
    }

    override fun onStateChange(state: IntArray?): Boolean {
        val initialProgress = progress
        val newProgress = if (state?.contains(android.R.attr.state_activated) == true) {
            1f
        } else {
            0f
        }
        progressAnim?.cancel()
        progressAnim = ValueAnimator.ofFloat(initialProgress, newProgress).apply {
            addUpdateListener {
                progress = animatedValue as Float
            }
            // scale the duration if was already running
            duration = (Math.abs(newProgress - initialProgress) * ANIM_DURATION).toLong()
            interpolator = interp
        }
        progressAnim?.start()
        return newProgress == initialProgress
    }
    override fun isStateful() = true

    override fun onBoundsChange(bounds: Rect?) {
        if (bounds == null) return
        update()
        val dLeft = (bounds.right - selectedMark.intrinsicWidth) / 2
        val dTop = (bounds.bottom - selectedMark.intrinsicHeight) / 2
        selectedMark.setBounds(
                dLeft,
                dTop,
                dLeft + selectedMark.intrinsicWidth,
                dTop + selectedMark.intrinsicHeight
        )
        // scale about the 'corner' of the check mark
        pivotX = (bounds.width() / 2f) - (3f / 24f * selectedMark.intrinsicWidth)
        pivotY = (bounds.height() / 2f) + (5f / 24f * selectedMark.intrinsicWidth)
    }

    override fun draw(canvas: Canvas) {
        canvas.drawPath(path, thumbPaint)
        if (progress > 0f) {
            canvas.drawPath(path, tintPaint)
            canvas.withScale(progress, progress, pivotX, pivotY) {
                selectedMark.draw(canvas)
            }
        }
    }

    override fun setAlpha(alpha: Int) {
        thumbPaint.alpha = alpha
    }

    override fun getOpacity() = PixelFormat.TRANSLUCENT

    override fun setColorFilter(filter: ColorFilter?) {
        thumbPaint.colorFilter = filter
    }
}