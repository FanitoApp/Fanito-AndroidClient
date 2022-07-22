// Generated by Dagger (https://dagger.dev).
package io.android.fanito_androidclient.ui.intro;

import javax.inject.Provider;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import io.android.fanito_androidclient.data.DataManager;

@DaggerGenerated
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class IntroViewModel_Factory implements Factory<IntroViewModel> {
  private final Provider<DataManager> dataManagerProvider;

  public IntroViewModel_Factory(Provider<DataManager> dataManagerProvider) {
    this.dataManagerProvider = dataManagerProvider;
  }

  @Override
  public IntroViewModel get() {
    return newInstance(dataManagerProvider.get());
  }

  public static IntroViewModel_Factory create(Provider<DataManager> dataManagerProvider) {
    return new IntroViewModel_Factory(dataManagerProvider);
  }

  public static IntroViewModel newInstance(DataManager dataManager) {
    return new IntroViewModel(dataManager);
  }
}
