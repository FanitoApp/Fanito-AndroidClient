package io.android.fanito_androidclient.ui.base

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import io.android.fanito_androidclient.utils.extensions.toast

abstract class BaseFragment<T : ViewDataBinding?, V : BaseViewModel<*>?> : Fragment(),
  BaseNavigator {

  var baseActivity: BaseActivity<*, *>? = null
    private set
  private var rootView: View? = null
  var viewDataBinding: T? = null
    private set
  abstract val viewModel: V?
  abstract val bindingVariable: Int

  @get:LayoutRes
  abstract val layoutId: Int

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setHasOptionsMenu(false)
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    viewDataBinding = DataBindingUtil.inflate(inflater, layoutId, container, false)
    rootView = viewDataBinding!!.root
    return rootView
  }

  override fun onDetach() {
    baseActivity = null
    super.onDetach()
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    viewDataBinding!!.setVariable(bindingVariable, viewModel)
    viewDataBinding!!.lifecycleOwner = this
    viewDataBinding!!.executePendingBindings()
  }

  fun openActivityOnTokenExpire() {
    if (baseActivity != null) {
      baseActivity!!.openActivityOnTokenExpire()
    }
  }

  override fun hideKeyboard() {
    view?.let {
      val imm = context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
      imm.hideSoftInputFromWindow(it.windowToken, 0)
    }
  }

  override fun toastMessage(@StringRes messageId: Int) = requireContext().toast(messageId)

  override fun toastMessage(message: String) = requireContext().toast(message)

  override fun back() = requireActivity().onBackPressed()
}