package ru.lex.alexander.testapp.injection.module;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.lex.alexander.testapp.data.remote.LocationApi;
import ru.lex.alexander.testapp.data.remote.WeatherApi;
import ru.lex.alexander.testapp.injection.ApplicationContext;

/**
 * Provide application-level dependencies.
 */
@Module
public class ApplicationModule {
    protected final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    @Singleton
    WeatherApi provideWeatherApi() {
        return WeatherApi.Creator.newWeatherApi();
    }

    @Provides
    @Singleton
    LocationApi provideLocationService(){
        return new LocationApi(mApplication);
    }

}
