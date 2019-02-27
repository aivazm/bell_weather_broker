package com.bell.weatherbroker;

import bell.commonmodel.model.WeatherView;
import bell.commonmodel.remote.WeatherTransmitter;
import com.bell.weatherbroker.service.WeatherService;
import com.caucho.hessian.server.HessianServlet;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;


/**
 * {@inheritDoc}
 */
@Slf4j
public class WeatherTransmitterImpl extends HessianServlet implements WeatherTransmitter {

    private final WeatherService service;

    @Inject
    public WeatherTransmitterImpl(WeatherService service) {
        this.service = service;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WeatherView getWeather(String cityName) {
        WeatherView view = null;
        if (cityName == null || cityName.equals("")) {
            log.info("Parameter cityName is empty");
        } else {
            view = service.getWeather(cityName);
        }
        return view;
    }
}
