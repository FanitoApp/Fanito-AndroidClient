// Generated by Dagger (https://dagger.dev).
package io.android.fanito_androidclient.di;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

@DaggerGenerated
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class AppModule_ProvideDatabaseNameFactory implements Factory<String> {
  @Override
  public String get() {
    return provideDatabaseName();
  }

  public static AppModule_ProvideDatabaseNameFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static String provideDatabaseName() {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideDatabaseName());
  }

  private static final class InstanceHolder {
    private static final AppModule_ProvideDatabaseNameFactory INSTANCE = new AppModule_ProvideDatabaseNameFactory();
  }
}