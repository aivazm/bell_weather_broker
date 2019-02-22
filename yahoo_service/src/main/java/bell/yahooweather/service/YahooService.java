package bell.yahooweather.service;


import bell.commonmodel.model.WeatherView;

import java.io.IOException;

public interface YahooService {
    WeatherView getWeatherFromYahoo(String cityName) throws IOException;
}
