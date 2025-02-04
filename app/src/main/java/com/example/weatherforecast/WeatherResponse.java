package com.example.weatherforecast;

public class WeatherResponse {
    public Location location;
    public Current current;

    public class Location {
        public String name;
        public String region;
        public String country;
        public double lat;
        public double lon;
        public String tz_id;
        public String localtime;
    }

    public class Current {
        public double temp_c;
        public Condition condition;
        public double wind_kph;
        public int humidity;
        public double feelslike_c;
    }

    public class Condition {
        public String text;
        public String icon;
        public int code;
    }
}