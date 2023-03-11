package io.android.fanito_androidclient.utils.extensions

import android.graphics.LinearGradient
import android.graphics.Shader
import android.text.TextPaint
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import io.android.fanito_androidclient.R


@BindingAdapter("gradient")
fun TextView.gradient(enable: Boolean) {
  if (!enable) return
  val paint: TextPaint = paint
  val width = paint.measureText(text.toString())

  val textShader: Shader = LinearGradient(
    0f, 0f, width, textSize, intArrayOf(
      ContextCompat.getColor(context, R.color.yellow),
      ContextCompat.getColor(context, R.color.orange),
    ), null, Shader.TileMode.CLAMP
  )
  paint.shader = textShader
}