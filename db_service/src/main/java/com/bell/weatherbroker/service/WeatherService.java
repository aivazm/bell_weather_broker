package com.bell.weatherbroker.service;

import bell.commonmodel.model.WeatherView;

/**
 * Сервис для работы с данными о погоде
 */
public interface WeatherService {
    /**
     * Преобразует объект WeatherView в Weather. Направляет объект Weather в WeatherRepository.
     *
     * Вызывается в DbServiceReceiver
     * @param weatherView
     * @return WeatherView
     */
    WeatherView add(WeatherView weatherView);

    /**
     * Получает объект Weather из WeatherRepository.
     * Преобразует объект Weather в WeatherView. Направляет объект WeatherView в WeatherRepository.
     * Вызывается в WeatherTransmitter
     * @param cityName
     * @return WeatherView
     */
    WeatherView getWeather(String cityName);
}
