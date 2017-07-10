package ru.lex.alexander.testapp.ui.login;

import ru.lex.alexander.testapp.ui.base.MvpView;

public interface LoginMvpView extends MvpView {

    void showWeatherMessage(String message);

    void showError(String message);

    boolean checkLocationPermission();
}
