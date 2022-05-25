// Generated by Dagger (https://dagger.dev).
package io.android.fanito_androidclient.di;

import javax.inject.Provider;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import io.android.fanito_androidclient.data.AppDataManager;
import io.android.fanito_androidclient.data.DataManager;

@DaggerGenerated
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class AppModule_ProvideDataManagerFactory implements Factory<DataManager> {
  private final Provider<AppDataManager> appDataManagerProvider;

  public AppModule_ProvideDataManagerFactory(Provider<AppDataManager> appDataManagerProvider) {
    this.appDataManagerProvider = appDataManagerProvider;
  }

  @Override
  public DataManager get() {
    return provideDataManager(appDataManagerProvider.get());
  }

  public static AppModule_ProvideDataManagerFactory create(
      Provider<AppDataManager> appDataManagerProvider) {
    return new AppModule_ProvideDataManagerFactory(appDataManagerProvider);
  }

  public static DataManager provideDataManager(AppDataManager appDataManager) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideDataManager(appDataManager));
  }
}
