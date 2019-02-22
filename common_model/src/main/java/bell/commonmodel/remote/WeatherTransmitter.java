package bell.commonmodel.remote;

import bell.commonmodel.model.WeatherView;

public interface WeatherTransmitter {
    WeatherView getWeather(String cityName);
}
