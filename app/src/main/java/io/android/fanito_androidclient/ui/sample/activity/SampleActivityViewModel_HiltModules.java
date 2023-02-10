package io.android.fanito_androidclient.ui.sample.activity;

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

@OriginatingElement(
    topLevelClass = SampleActivityViewModel.class
)
public final class SampleActivityViewModel_HiltModules {
  private SampleActivityViewModel_HiltModules() {
  }

  @Module
  @InstallIn(ViewModelComponent.class)
  public abstract static class BindsModule {
    private BindsModule() {
    }

    @Binds
    @IntoMap
    @StringKey("io.android.fanito_androidclient.ui.sample.activity.SampleActivityViewModel")
    @HiltViewModelMap
    public abstract ViewModel binds(SampleActivityViewModel vm);
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
      return "io.android.fanito_androidclient.ui.sample.activity.SampleActivityViewModel";
    }
  }
}
