package com.bell.weatherbroker.repository;

import com.bell.weatherbroker.model.Weather;

public interface WeatherRepository {

    void add(Weather model);

    Weather findByCityName(String cityName);
}
