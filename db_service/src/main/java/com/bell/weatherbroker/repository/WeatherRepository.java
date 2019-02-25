package com.bell.weatherbroker.repository;

import com.bell.weatherbroker.model.Weather;

/**
 * Класс для работы с базой данных
 */
public interface WeatherRepository {

    /**
     * Добвляет объект model в базу данных, возвращает этот же объект
     * @param model
     * @return
     */
    Weather add(Weather model);

    /**
     * Поиск в базе данных по наименованию населенного пункта
     * @param cityName
     * @return
     */
    Weather findByCityName(String cityName);
}
