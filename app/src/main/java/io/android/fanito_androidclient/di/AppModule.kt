package io.android.fanito_androidclient.di


import android.app.Application
import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.android.fanito_androidclient.BuildConfig
import io.android.fanito_androidclient.utils.RetryCallAdapterFactory
import io.android.fanito_androidclient.data.AppDataManager
import io.android.fanito_androidclient.data.DataManager
import io.android.fanito_androidclient.data.local.prefs.AppPreferencesHelper
import io.android.fanito_androidclient.data.local.prefs.PreferencesHelper
import io.android.fanito_androidclient.data.remote.ApiHelper
import io.android.fanito_androidclient.data.remote.ApiServices
import io.android.fanito_androidclient.data.remote.AppApiHelper
import io.android.fanito_androidclient.data.resource.AppResourceHelper
import io.android.fanito_androidclient.data.resource.ResourceHelper
import io.android.fanito_androidclient.di.annotation.DatabaseInfo
import io.android.fanito_androidclient.di.annotation.PreferenceInfo
import io.android.fanito_androidclient.utils.AppConstants
import io.android.fanito_androidclient.utils.HeaderInterceptor
import io.android.fanito_androidclient.utils.NetworkConnectionInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(
        networkConnectionInterceptor: NetworkConnectionInterceptor,
        headerInterceptor: HeaderInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(networkConnectionInterceptor)
        .addInterceptor(headerInterceptor)
        .build()

    @Singleton
    @Provides
    fun provideApiServices(
        gson: Gson,
        okHttpclient: OkHttpClient
    ): ApiServices = Retrofit.Builder()
        .client(okHttpclient)
        .baseUrl(BuildConfig.BASE_URL)
        .addCallAdapterFactory(RetryCallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
        .create(ApiServices::class.java)

    @Provides
    @Singleton
    fun provideApiHelper(appApiHelper: AppApiHelper): ApiHelper = appApiHelper

    @Provides
    @Singleton
    fun provideContext(application: Application): Context = application

    @Provides
    @Singleton
    fun provideDataManager(appDataManager: AppDataManager): DataManager = appDataManager

    @Provides
    @DatabaseInfo
    fun provideDatabaseName(): String = AppConstants.DB_NAME

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()

    @Provides
    @Singleton
    fun provideResourceHelper(appResourceHelper: AppResourceHelper): ResourceHelper =
        appResourceHelper

    @Provides
    @PreferenceInfo
    fun providePreferenceName(): String = AppConstants.PREF_NAME

    @Provides
    @Singleton
    fun providePreferencesHelper(appPreferencesHelper: AppPreferencesHelper): PreferencesHelper =
        appPreferencesHelper

}