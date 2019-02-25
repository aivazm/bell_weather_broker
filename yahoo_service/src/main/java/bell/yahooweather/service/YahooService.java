package bell.yahooweather.service;


import bell.commonmodel.model.WeatherView;

import java.io.IOException;

/**
 * Сервис запросов и обработки ответов сервера Yahoo
 */
public interface YahooService {
    /**
     * Метод принимает наименование населенного пункта cityName. Формирует запрос на yahoo.com.
     * Из полученного ответа формируется объект WeatherView.
     * @param cityName
     * @return WeatherView
     * @throws IOException
     */
    WeatherView getWeatherFromYahoo(String cityName) throws IOException;
}
