package io.android.fanito_androidclient.utils.extensions

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.app.Activity
import android.os.Build
import android.util.DisplayMetrics
import android.util.TypedValue
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import kotlin.math.pow
import kotlin.math.sqrt


fun Activity.coloredStatusBar(@ColorRes color: Int, animate: Boolean = false) {
  if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) return
  if (!animate) {
    window.statusBarColor = ContextCompat.getColor(this, color)
    return
  }

  // with animation
  val colorFrom = window.statusBarColor
  val colorTo = ContextCompat.getColor(this, color)
  val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), colorFrom, colorTo)
  colorAnimation.duration = 400
  colorAnimation.addUpdateListener { animator ->
    window.statusBarColor = animator.animatedValue as Int
  }
  colorAnimation.start()
}

fun Activity.isTablet(): Boolean {
  val display = windowManager.defaultDisplay
  val metrics = DisplayMetrics()
  display.getMetrics(metrics)
  val widthInches = metrics.widthPixels / metrics.xdpi
  val heightInches = metrics.heightPixels / metrics.ydpi
  val diagonalInches =
    sqrt(widthInches.toDouble().pow(2.0) + heightInches.toDouble().pow(2.0))
  return diagonalInches >= 7.0
}

fun Activity.actionBarSize(): Int {
  val tv = TypedValue()
  return if (theme.resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
    TypedValue.complexToDimensionPixelSize(tv.data, resources.displayMetrics)
  } else 0
}