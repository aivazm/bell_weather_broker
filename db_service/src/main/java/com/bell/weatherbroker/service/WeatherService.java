package com.bell.weatherbroker.service;

import bell.commonmodel.model.WeatherView;

public interface WeatherService {
    void add(WeatherView weatherView);

    WeatherView getWeather(String cityName);
}
