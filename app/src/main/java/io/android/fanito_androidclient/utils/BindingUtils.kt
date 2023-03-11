package io.android.fanito_androidclient.utils

import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import saman.zamani.persiandate.PersianDate
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object BindingUtils {

  @JvmStatic
  @BindingAdapter(value = ["imageUrl", "placeHolder"], requireAll = false)
  fun ImageView.setImageUrl(url: String?, placeHolder: Drawable?) {
    if (placeHolder != null)
      Glide.with(context).load(url).placeholder(placeHolder).error(placeHolder).into(this)
    else Glide.with(context).load(url).into(this)
  }

  @JvmStatic
  @BindingAdapter(value = ["digitSeparatorText", "suffix"], requireAll = false)
  fun TextView.digitSeparatorText(value: Long, suffix: String?) {
    val s = suffix ?: ""
    text = "${CommonUtils.digitSeparator(value)} $s"
  }

  @JvmStatic
  @BindingAdapter(value = ["setPersianDate", "shortDateFormat"], requireAll = false)
  fun TextView.setPersianDate(setPersianDate: String?, shortDateFormat: Boolean?) {
    if(setPersianDate==null)
      return
    val date = CommonUtils.dateStringToDate(setPersianDate)
    text = if (date == null) ""
    else {
      val pDate = PersianDate(date)
      if (shortDateFormat == null || !shortDateFormat) pDate.toString()
      else "${pDate.shYear}/${pDate.shMonth}/${pDate.shDay} ${pDate.hour}:${pDate.minute}:${pDate.second}"
    }
  }
}