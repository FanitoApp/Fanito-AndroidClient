// Generated by Dagger (https://dagger.dev).
package io.android.fanito_androidclient.ui.intro;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

@DaggerGenerated
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class IntroViewModel_HiltModules_KeyModule_ProvideFactory implements Factory<String> {
  @Override
  public String get() {
    return provide();
  }

  public static IntroViewModel_HiltModules_KeyModule_ProvideFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static String provide() {
    return Preconditions.checkNotNullFromProvides(IntroViewModel_HiltModules.KeyModule.provide());
  }

  private static final class InstanceHolder {
    private static final IntroViewModel_HiltModules_KeyModule_ProvideFactory INSTANCE = new IntroViewModel_HiltModules_KeyModule_ProvideFactory();
  }
}
