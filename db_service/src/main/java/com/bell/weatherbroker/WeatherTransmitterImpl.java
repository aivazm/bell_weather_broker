package com.bell.weatherbroker;

import bell.commonmodel.model.WeatherView;
import bell.commonmodel.remote.WeatherTransmitter;
import com.bell.weatherbroker.service.WeatherService;
import com.caucho.hessian.server.HessianServlet;

import javax.inject.Inject;


/**
 * {@inheritDoc}
 */
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
        return service.getWeather(cityName);
    }
}
