package com.bell.weatherbroker.service;

import bell.commonmodel.model.WeatherView;
import com.bell.weatherbroker.model.Weather;
import com.bell.weatherbroker.repository.WeatherRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

/**
 * {@inheritDoc}
 */
@Slf4j
@RequestScoped
public class WeatherServiceImpl implements WeatherService {

    private WeatherRepository repository;
    private Validator validator;

    @Inject
    public WeatherServiceImpl(WeatherRepository repository, Validator validator) {
        this.repository = repository;
        this.validator = validator;
    }

    public WeatherServiceImpl() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WeatherView add(WeatherView weatherView) {
        if (weatherView == null) {
            throw new RuntimeException("Parameter weatherView is null");
        }
        Weather model = Weather
                .builder()
                .cityName(weatherView.getCity())
                .countryName(weatherView.getCountry())
                .windSpeed(weatherView.getWindSpeed())
                .condition(weatherView.getCondition())
                .temperature(weatherView.getTemperature())
                .build();

        StringBuilder message = new StringBuilder();
        Set<ConstraintViolation<Weather>> validate = validator.validate(model);
        if (!validate.isEmpty()) {
            for (ConstraintViolation<Weather> violation : validate) {
                message.append(violation.getMessage());
                message.append("; ");
            }
        }
        if (message.length() > 0) {
            throw new RuntimeException("Validation error: " + message.toString().trim());
        }

        return convertModelToView(repository.add(model));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WeatherView getWeather(String cityName) {
        if (StringUtils.isBlank(cityName)) {
            throw new RuntimeException("Parameter cityName is null or empty");
        }
        Weather model = repository.findByCityName(cityName);
        return convertModelToView(model);
    }

    private WeatherView convertModelToView(Weather model) {
        WeatherView view = null;
        if (model != null) {
            view = WeatherView
                    .builder()
                    .city(model.getCityName())
                    .country(model.getCountryName())
                    .windSpeed(model.getWindSpeed())
                    .condition(model.getCondition())
                    .temperature(model.getTemperature())
                    .build();
        }
        return view;
    }
}
