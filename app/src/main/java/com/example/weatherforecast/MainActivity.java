package com.example.weatherforecast;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView tvLocation, tvTemperature, tvCondition, tvForecast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        tvLocation = findViewById(R.id.tvLocation);
        tvTemperature = findViewById(R.id.tvTemperature);
        tvCondition = findViewById(R.id.tvCondition);
        tvForecast = findViewById(R.id.tvForecast);

        // --- WeatherAPI.com Integration ---
        WeatherRepository weatherRepo = new WeatherRepository();
        weatherRepo.fetchWeather("London", new WeatherRepository.WeatherCallback() {
            @Override
            public void onSuccess(WeatherResponse result) {
                // Update UI on the main thread
                runOnUiThread(() -> {
                    tvLocation.setText(result.location.name + ", " + result.location.country);
                    tvTemperature.setText("Temperature: " + result.current.temp_c + "°C");
                    tvCondition.setText("Condition: " + result.current.condition.text);
                });
            }
            @Override
            public void onError(String error) {
                runOnUiThread(() -> {
                    tvLocation.setText("Error fetching weather");
                    tvTemperature.setText("");
                    tvCondition.setText(error);
                });
            }
        });

        // --- OpenMeteo Integration ---
        OpenMeteoRepository openMeteoRepo = new OpenMeteoRepository();
        // Example coordinates: 52.52 (latitude), 13.41 (longitude)
        openMeteoRepo.fetchWeatherForecast(52.52, 13.41, new OpenMeteoRepository.MeteoCallback() {
            @Override
            public void onSuccess(OpenMeteoWeatherResponse result) {
                runOnUiThread(() -> {
                    // Display the first 5 hourly forecasts (if available)
                    if (result.hourly != null && result.hourly.temperature_2m != null && result.hourly.time != null) {
                        StringBuilder forecastText = new StringBuilder();
                        int count = Math.min(result.hourly.temperature_2m.size(), 5);
                        for (int i = 0; i < count; i++) {
                            forecastText.append(result.hourly.time.get(i))
                                    .append(": ")
                                    .append(result.hourly.temperature_2m.get(i))
                                    .append("°C\n");
                        }
                        tvForecast.setText(forecastText.toString());
                    } else {
                        tvForecast.setText("No forecast data available.");
                    }
                });
            }
            @Override
            public void onError(String error) {
                runOnUiThread(() -> tvForecast.setText("Error fetching forecast: " + error));
            }
        });
    }
}