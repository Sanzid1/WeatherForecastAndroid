package com.example.weatherforecast;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Callback;
import retrofit2.Response;

public class OpenMeteoRepository {
    private static final String BASE_URL = "https://api.open-meteo.com/v1/";
    private OpenMeteoApiService apiService;

    public OpenMeteoRepository() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(OpenMeteoApiService.class);
    }

    public interface MeteoCallback {
        void onSuccess(OpenMeteoWeatherResponse result);
        void onError(String error);
    }

    public void fetchWeatherForecast(double latitude, double longitude, MeteoCallback callback) {
        Call<OpenMeteoWeatherResponse> call = apiService.getWeatherForecast(latitude, longitude, "temperature_2m");
        call.enqueue(new Callback<OpenMeteoWeatherResponse>() {
            @Override
            public void onResponse(Call<OpenMeteoWeatherResponse> call, Response<OpenMeteoWeatherResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("OpenMeteo Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<OpenMeteoWeatherResponse> call, Throwable t) {
                callback.onError("OpenMeteo Failure: " + t.getMessage());
            }
        });
    }
}