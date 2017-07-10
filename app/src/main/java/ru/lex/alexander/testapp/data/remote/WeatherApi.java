package ru.lex.alexander.testapp.data.remote;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import io.reactivex.Observable;
import retrofit2.http.Query;
import ru.lex.alexander.testapp.data.model.WeatherReport;

public interface WeatherApi {

    String ENDPOINT = "http://api.openweathermap.org/data/2.5/";

    String LANGUAGE_EN = "en";
    String LANGUAGE_RU = "ru";
    String UNITS_IMPERIAL = "imperial";
    String UNITS_METRICS = "metrics";

    @GET("weather")
    Observable<WeatherReport> getWeather(
            @Query("lat") double lat,
            @Query("lon") double lon,
            @Query("lang") String language,
            @Query("units") String units,
            @Query("appid") String apiKey);

    /******** Helper class that sets up a new services *******/
    class Creator {

        public static WeatherApi newWeatherApi() {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(WeatherApi.ENDPOINT)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
            return retrofit.create(WeatherApi.class);
        }
    }
}
