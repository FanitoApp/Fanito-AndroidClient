package io.android.fanito_androidclient.utils

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.provider.Settings
import android.util.Patterns
import io.android.fanito_androidclient.R
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object CommonUtils {
  @SuppressLint("all")
  fun getDeviceId(context: Context): String {
    return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
  }

  val timestamp: String
    get() = SimpleDateFormat(AppConstants.TIMESTAMP_FORMAT, Locale.US).format(Date())

  fun isEmailValid(email: String): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(email).matches()
  }

  fun showLoadingDialog(context: Context): ProgressDialog {
    val progressDialog = ProgressDialog(context)
    progressDialog.show()
    progressDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    progressDialog.setContentView(R.layout.progress_dialog)
    progressDialog.isIndeterminate = true
    progressDialog.setCancelable(false)
    progressDialog.setCanceledOnTouchOutside(false)
    return progressDialog
  }

  fun digitSeparator(value: Long): String {
    return try {
      val formatter: DecimalFormat = NumberFormat.getInstance(Locale.US) as DecimalFormat
      formatter.applyPattern("#,###")
      formatter.format(value)
    } catch (e: Exception) {
      "0"
    }
  }


  /**
   *Convert date string with desired format to date object
   */
  fun dateStringToDate(dateString: String?): Date? {
    val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US)
    return try {
      format.parse(dateString)
    } catch (e: ParseException) {
      e.printStackTrace()
      null
    }
  }

  /**
   *Convert date to date string with desired format
   */
  fun dateToString(date: Date): String {
    val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US)
    return format.format(date)
  }
}