package ru.lex.alexander.testapp.ui.login;

import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ru.lex.alexander.testapp.data.DataManager;
import ru.lex.alexander.testapp.data.model.WeatherReport;
import ru.lex.alexander.testapp.data.remote.LocationApi;
import ru.lex.alexander.testapp.data.remote.WeatherApi;
import ru.lex.alexander.testapp.injection.ConfigPersistent;
import ru.lex.alexander.testapp.ui.base.BasePresenter;

@ConfigPersistent
public class LoginPresenter extends BasePresenter<LoginMvpView> implements LocationApi.LocationApiListener {
    private final String API_KEY = "99eeacffccbc79d0cf362f9699c274ed";

    @Inject
    LocationApi mLocationApi;

    private final DataManager mDataManager;
    private Disposable mDisposable;

    @Inject
    public LoginPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(LoginMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mDisposable != null)
            mDisposable.dispose();
    }

    public void getWeather() {
        checkViewAttached();

        mLocationApi.getLocation(this);
    }

    private void loadWeather(double lat, double lon) {
        mDataManager.getWeather(lat, lon, WeatherApi.LANGUAGE_RU, WeatherApi.UNITS_METRICS, API_KEY)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<WeatherReport>() {
                    @Override
                    public void onSubscribe(Disposable disposable) {
                        LoginPresenter.this.mDisposable = disposable;
                    }

                    @Override
                    public void onNext(WeatherReport weatherReport) {
                        showWeatherMessage(weatherReport);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        getMvpView().showError(throwable.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    private void showWeatherMessage(WeatherReport weatherReport) {
        int temp = (int) (weatherReport.getMain().getTemp() - 273);
        String cityName = weatherReport.getName();
        String weatherDescription = weatherReport.getWeather().get(0).getDescription();
        String weatherMessage = String.format(Locale.getDefault(), "%s: %+d (%s)", cityName, temp, weatherDescription);
        getMvpView().showWeatherMessage(weatherMessage);
    }

    @Override
    public void onLocationResult(double lat, double lon) {
        loadWeather(lat, lon);
    }
}
