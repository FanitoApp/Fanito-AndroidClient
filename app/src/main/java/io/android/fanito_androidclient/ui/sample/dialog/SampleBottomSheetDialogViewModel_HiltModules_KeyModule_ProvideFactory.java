// Generated by Dagger (https://dagger.dev).
package io.android.fanito_androidclient.ui.sample.dialog;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

@DaggerGenerated
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class SampleBottomSheetDialogViewModel_HiltModules_KeyModule_ProvideFactory implements Factory<String> {
  @Override
  public String get() {
    return provide();
  }

  public static SampleBottomSheetDialogViewModel_HiltModules_KeyModule_ProvideFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static String provide() {
    return Preconditions.checkNotNullFromProvides(SampleBottomSheetDialogViewModel_HiltModules.KeyModule.provide());
  }

  private static final class InstanceHolder {
    private static final SampleBottomSheetDialogViewModel_HiltModules_KeyModule_ProvideFactory INSTANCE = new SampleBottomSheetDialogViewModel_HiltModules_KeyModule_ProvideFactory();
  }
}
