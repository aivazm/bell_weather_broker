package bell.commonmodel.remote;

import bell.commonmodel.model.WeatherView;

/**
 * Общий ресурс. Интерфейс для соединения hessian remote proxy
 */
public interface WeatherTransmitter {
    /**
     * Получить WeatherView из WeatherService
     * @param cityName
     * @return
     */
    WeatherView getWeather(String cityName);
}
