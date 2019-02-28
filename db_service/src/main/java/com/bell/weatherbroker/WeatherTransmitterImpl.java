package com.bell.weatherbroker;

import bell.commonmodel.model.WeatherView;
import bell.commonmodel.remote.WeatherTransmitter;
import com.bell.weatherbroker.service.WeatherService;
import com.caucho.hessian.server.HessianServlet;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

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
        if (StringUtils.isBlank(cityName)) {
            throw new RuntimeException("Parameter cityName is empty");
        }
        return service.getWeather(cityName);

    }
}
