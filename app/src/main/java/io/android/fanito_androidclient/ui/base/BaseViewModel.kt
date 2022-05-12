package io.android.fanito_androidclient.ui.base

import androidx.lifecycle.ViewModel
import io.fanito.android.data.DataManager
import java.lang.ref.WeakReference

abstract class BaseViewModel<N : BaseNavigator>(val dataManager: DataManager) : ViewModel() {

  private var mNavigator: WeakReference<N>? = null

  val navigator: N? get() = mNavigator!!.get()

  fun setNavigator(navigator: N) {
    mNavigator = WeakReference(navigator)
  }
}