package com.example.weatherforecast;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApiService {
    @GET("current.json")
    Call<WeatherResponse> getCurrentWeather(@Query("key") String key,
                                            @Query("q") String query);
}