// Generated by Dagger (https://dagger.dev).
package io.android.fanito_androidclient.ui.main.wallet;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

@DaggerGenerated
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class WalletViewModel_HiltModules_KeyModule_ProvideFactory implements Factory<String> {
  @Override
  public String get() {
    return provide();
  }

  public static WalletViewModel_HiltModules_KeyModule_ProvideFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static String provide() {
    return Preconditions.checkNotNullFromProvides(WalletViewModel_HiltModules.KeyModule.provide());
  }

  private static final class InstanceHolder {
    private static final WalletViewModel_HiltModules_KeyModule_ProvideFactory INSTANCE = new WalletViewModel_HiltModules_KeyModule_ProvideFactory();
  }
}
