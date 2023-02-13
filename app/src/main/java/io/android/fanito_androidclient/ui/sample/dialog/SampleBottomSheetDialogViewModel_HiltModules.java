package io.android.fanito_androidclient.ui.sample.dialog;

import androidx.lifecycle.ViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityRetainedComponent;
import dagger.hilt.android.components.ViewModelComponent;
import dagger.hilt.android.internal.lifecycle.HiltViewModelMap;
import dagger.hilt.codegen.OriginatingElement;
import dagger.multibindings.IntoMap;
import dagger.multibindings.IntoSet;
import dagger.multibindings.StringKey;
import io.android.fanito_androidclient.ui.sample.dialog.SampleBottomSheetDialogViewModel;

@OriginatingElement(
    topLevelClass = SampleBottomSheetDialogViewModel.class
)
public final class SampleBottomSheetDialogViewModel_HiltModules {
  private SampleBottomSheetDialogViewModel_HiltModules() {
  }

  @Module
  @InstallIn(ViewModelComponent.class)
  public abstract static class BindsModule {
    private BindsModule() {
    }

    @Binds
    @IntoMap
    @StringKey("io.android.fanito_androidclient.ui.sample.dialog.SampleBottomSheetDialogViewModel")
    @HiltViewModelMap
    public abstract ViewModel binds(SampleBottomSheetDialogViewModel vm);
  }

  @Module
  @InstallIn(ActivityRetainedComponent.class)
  public static final class KeyModule {
    private KeyModule() {
    }

    @Provides
    @IntoSet
    @HiltViewModelMap.KeySet
    public static String provide() {
      return "io.android.fanito_androidclient.ui.sample.dialog.SampleBottomSheetDialogViewModel";
    }
  }
}
