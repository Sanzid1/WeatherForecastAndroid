package com.example.weatherforecast;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherRepository {
    private static final String BASE_URL = "https://api.weatherapi.com/v1/";
    private static final String API_KEY = "08cc3500c9c5477fb4d125254250402";
    private WeatherApiService apiService;

    public WeatherRepository() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(WeatherApiService.class);
    }

    public interface WeatherCallback {
        void onSuccess(WeatherResponse result);
        void onError(String error);
    }

    public void fetchWeather(String location, WeatherCallback callback) {
        Call<WeatherResponse> call = apiService.getCurrentWeather(API_KEY, location);
        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("WeatherAPI Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                callback.onError("WeatherAPI Failure: " + t.getMessage());
            }
        });
    }
}
