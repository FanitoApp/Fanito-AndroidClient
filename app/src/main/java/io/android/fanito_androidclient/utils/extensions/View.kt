package io.fanito.android.utils.extensions

import android.animation.Animator
import android.animation.ValueAnimator
import android.view.View
import android.view.View.*
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.LinearLayout
import androidx.databinding.BindingAdapter
import com.google.android.material.snackbar.Snackbar


fun View.changeVisibility(visible: Boolean) {
  visibility = if (visible) VISIBLE else GONE
}

fun View.visible() {
  visibility = VISIBLE
}

fun View.invisible() {
  visibility = INVISIBLE
}

fun View.gone() {
  visibility = GONE
}

fun View.isVisible(): Boolean {
  return visibility == VISIBLE
}

fun View.isInvisible(): Boolean {
  return visibility == INVISIBLE
}

fun View.isGone(): Boolean {
  return visibility == GONE
}

fun View.isInvisibleOrGone(): Boolean {
  return visibility == GONE || visibility == INVISIBLE
}

fun View.snackBar(
  message: String,
  duration: Int = Snackbar.LENGTH_SHORT,
  functionsToApply: (() -> Unit)? = null
) {
  Snackbar.make(this, message, duration).apply {
    functionsToApply?.invoke()
  }.show()
}

/**
 *Changing alpha view with animation
 *
 * @param from The alpha value of the view at the start of the animation
 * @param to The alpha value of the view at the end of the animation
 * @param duration Animation duration
 * @param beforeAnimate The operation that needs to be done before starting the animation
 * @param afterAnimate The operation that needs to be done after the animation is finished
 */
fun View.animateAlpha(
  from: Float,
  to: Float,
  duration: Long,
  beforeAnimate: (() -> Unit)? = null,
  afterAnimate: (() -> Unit)? = null
) {
  ValueAnimator.ofFloat(from, to).apply {
    this.duration = duration
    this.addUpdateListener { alpha = it.animatedValue as Float }
    this.addListener(object : Animator.AnimatorListener {
      override fun onAnimationStart(p0: Animator?) {
        beforeAnimate?.invoke()
      }

      override fun onAnimationEnd(p0: Animator?) {
        afterAnimate?.invoke()
      }

      override fun onAnimationCancel(p0: Animator?) {}
      override fun onAnimationRepeat(p0: Animator?) {}
    })
  }.start()
}

@BindingAdapter("android:layout_weight")
fun View.setWeight(weight: Float) {
  val params = layoutParams
  (params as LinearLayout.LayoutParams).weight = weight
  layoutParams = params
}

@BindingAdapter("android:layout_width")
fun View.setWidth(isWrapContent: Boolean) {
  val params = layoutParams
  params.width = if (isWrapContent) WRAP_CONTENT else 0
  (params as LinearLayout.LayoutParams).weight = if (isWrapContent) 0F else 1F
  layoutParams = params
}