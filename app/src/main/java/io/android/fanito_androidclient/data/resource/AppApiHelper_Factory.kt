package io.android.fanito_androidclient.data.resource
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import javax.inject.Provider;

@DaggerGenerated
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class AppApiHelper_Factory implements Factory<AppApiHelper> {
    private final Provider<ApiServices> apiServicesProvider;

    public AppApiHelper_Factory(Provider<ApiServices> apiServicesProvider) {
        this.apiServicesProvider = apiServicesProvider;
    }

    @Override
    public AppApiHelper get() {
        return newInstance(apiServicesProvider.get());
    }

    public static AppApiHelper_Factory create(Provider<ApiServices> apiServicesProvider) {
        return new AppApiHelper_Factory(apiServicesProvider);
    }

    public static AppApiHelper newInstance(ApiServices apiServices) {
        return new AppApiHelper(apiServices);
    }
}