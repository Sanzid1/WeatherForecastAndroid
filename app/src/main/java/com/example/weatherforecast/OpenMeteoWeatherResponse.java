package com.example.weatherforecast;

import java.util.List;

public class OpenMeteoWeatherResponse {
    public Hourly hourly;

    public class Hourly {
        public List<String> time;
        public List<Double> temperature_2m;
    }
}