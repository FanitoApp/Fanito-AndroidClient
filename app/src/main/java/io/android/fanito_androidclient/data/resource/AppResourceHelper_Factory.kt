package io.android.fanito_androidclient.data.resource

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import javax.inject.Provider;

@DaggerGenerated
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class AppResourceHelper_Factory implements Factory<AppResourceHelper> {
    private final Provider<Context> contextProvider;

    public AppResourceHelper_Factory(Provider<Context> contextProvider) {
        this.contextProvider = contextProvider;
    }

    @Override
    public AppResourceHelper get() {
        return newInstance(contextProvider.get());
    }

    public static AppResourceHelper_Factory create(Provider<Context> contextProvider) {
        return new AppResourceHelper_Factory(contextProvider);
    }

    public static AppResourceHelper newInstance(Context context) {
        return new AppResourceHelper(context);
    }
}