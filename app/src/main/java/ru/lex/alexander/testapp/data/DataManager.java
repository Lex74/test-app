package ru.lex.alexander.testapp.data;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import ru.lex.alexander.testapp.data.model.WeatherReport;
import ru.lex.alexander.testapp.data.remote.LocationApi;
import ru.lex.alexander.testapp.data.remote.WeatherApi;

@Singleton
public class DataManager {

    private final WeatherApi mWeatherApi;
    private final LocationApi mLocationApi;

    @Inject
    public DataManager(WeatherApi weatherApi, LocationApi locationApi) {
        mWeatherApi = weatherApi;
        mLocationApi = locationApi;
    }

    public Observable<WeatherReport> getWeather(double lat, double lon, String language, String units, String apiKey) {
        return mWeatherApi.getWeather(lat, lon, language, units, apiKey);
    }
}
