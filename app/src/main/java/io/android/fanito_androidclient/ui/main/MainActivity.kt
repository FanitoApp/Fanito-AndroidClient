package io.android.fanito_androidclient.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import androidx.activity.viewModels
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.databinding.library.baseAdapters.BR
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import dagger.hilt.android.AndroidEntryPoint
import io.android.fanito_androidclient.BuildConfig
import io.android.fanito_androidclient.R
import io.android.fanito_androidclient.databinding.ActivityMainBinding
import io.android.fanito_androidclient.databinding.ContentMainBinding
import io.android.fanito_androidclient.databinding.LayoutDrawerHeaderBinding
import io.android.fanito_androidclient.databinding.LayoutDrawerItemsBinding
import io.android.fanito_androidclient.ui.base.BaseActivity
import io.android.fanito_androidclient.ui.intro.IntroActivity
import io.android.fanito_androidclient.utils.extensions.coloredStatusBar

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(), MainNavigator,
  NavController.OnDestinationChangedListener {

  override val viewModel: MainViewModel by viewModels()
  override val bindingVariable: Int get() = BR.viewModel
  override val layoutId: Int get() = R.layout.activity_main

  private lateinit var contentMain: ContentMainBinding
  private lateinit var drawerHeader: LayoutDrawerHeaderBinding
  private lateinit var drawerItems: LayoutDrawerItemsBinding

  companion object {
    private const val TAG = "MainActivity"
    fun newIntent(context: Context) = Intent(context, MainActivity::class.java)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    viewModel.setNavigator(this)
    viewDataBinding?.let {
      contentMain = it.includeContainMain
      drawerHeader = it.includeNavDrawerHeader
      drawerItems = it.includeNavDrawerItems
    }
    setUpDrawer()
    setUpNavigation()
  }

  private fun setUpDrawer() {
    viewDataBinding?.let {
      viewModel.userPersonal.observe(this, {
        drawerHeader.userName.text = "${it.firstName} ${it.lastName}"
        drawerHeader.userNumber.text = it.phone
      })
      viewModel.userTokensCount.observe(this, { drawerHeader.token.text = "$it" })
      drawerHeader.close.setOnClickListener { closeDrawer() }
      drawerItems.signOut.setOnClickListener { viewModel.signOut() }
      drawerItems.version.text = "${getString(R.string.version)} ${BuildConfig.VERSION_NAME}"
    }
  }

  private fun setUpNavigation() {
    contentMain.bottomNavigation.itemIconTintList = null
    val navHostFragment =
      supportFragmentManager.findFragmentById(R.id.main_nav_host_fragment) as NavHostFragment
    NavigationUI.setupWithNavController(
      contentMain.bottomNavigation,
      navHostFragment.navController
    )
    navHostFragment.navController.addOnDestinationChangedListener(this)
    contentMain.bottomNavigation.menu.findItem(R.id.hunt).isEnabled = false
    contentMain.bottomNavigation.menu.findItem(R.id.exchange).isEnabled = false
  }

  override fun onBackPressed() {
    viewDataBinding?.let {
      if (it.drawerLayout.isDrawerOpen(Gravity.RIGHT)) {
        closeDrawer()
        return
      }
    }
    super.onBackPressed()
  }

  override fun onDestinationChanged(
    controller: NavController,
    destination: NavDestination,
    arguments: Bundle?
  ) {

    when (destination.label) {
      getString(R.string.wallet_label) -> {
        changeBackgroundColor(R.color.main_background)
        coloredStatusBar(R.color.main_background, animate = true)
      }
      else -> {
        changeBackgroundColor(R.color.menu_background_color)
        coloredStatusBar(R.color.menu_background_color, animate = true)
      }
    }
  }

  private fun changeBackgroundColor(@ColorRes color: Int) {
    contentMain.root.setBackgroundColor(ContextCompat.getColor(this, color))
  }

  fun openDrawer() = viewDataBinding?.drawerLayout?.openDrawer(Gravity.RIGHT)

  private fun closeDrawer() = viewDataBinding?.drawerLayout?.closeDrawer(Gravity.RIGHT)

  override fun goToIntro() {
    startActivity(IntroActivity.newIntent(this))
    this.finish()
  }
}