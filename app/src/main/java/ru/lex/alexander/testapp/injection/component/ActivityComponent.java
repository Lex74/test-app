package ru.lex.alexander.testapp.injection.component;

import dagger.Subcomponent;
import ru.lex.alexander.testapp.data.remote.LocationApi;
import ru.lex.alexander.testapp.injection.PerActivity;
import ru.lex.alexander.testapp.injection.module.ActivityModule;
import ru.lex.alexander.testapp.ui.login.LoginActivity;

/**
 * This component inject dependencies to all Activities across the application
 */
@PerActivity
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(LoginActivity loginActivity);
}
