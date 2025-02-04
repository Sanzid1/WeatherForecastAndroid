package com.example.weatherforecast;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenMeteoApiService {
    @GET("forecast")
    Call<OpenMeteoWeatherResponse> getWeatherForecast(@Query("latitude") double latitude,
                                                      @Query("longitude") double longitude,
                                                      @Query("hourly") String hourly);
}