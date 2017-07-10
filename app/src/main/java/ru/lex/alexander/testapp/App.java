package ru.lex.alexander.testapp;

import android.app.Application;
import android.content.Context;

import timber.log.Timber;
import ru.lex.alexander.testapp.injection.component.ApplicationComponent;
import ru.lex.alexander.testapp.injection.component.DaggerApplicationComponent;
import ru.lex.alexander.testapp.injection.module.ApplicationModule;

public class App extends Application  {

    ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    public static App get(Context context) {
        return (App) context.getApplicationContext();
    }

    public ApplicationComponent getComponent() {
        if (mApplicationComponent == null) {
            mApplicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
        }
        return mApplicationComponent;
    }

    // Needed to replace the component with a test specific one
    public void setComponent(ApplicationComponent applicationComponent) {
        mApplicationComponent = applicationComponent;
    }
}
