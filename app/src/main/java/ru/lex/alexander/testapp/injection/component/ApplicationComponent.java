package ru.lex.alexander.testapp.injection.component;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import ru.lex.alexander.testapp.data.DataManager;
import ru.lex.alexander.testapp.data.remote.LocationApi;
import ru.lex.alexander.testapp.data.remote.WeatherApi;
import ru.lex.alexander.testapp.injection.ApplicationContext;
import ru.lex.alexander.testapp.injection.module.ApplicationModule;
import ru.lex.alexander.testapp.util.RxEventBus;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    @ApplicationContext Context context();
    Application application();
    WeatherApi weatherApi();
    DataManager dataManager();
    RxEventBus eventBus();
    LocationApi locationService();

}
