package com.bell.weatherbroker.service;

import bell.commonmodel.model.WeatherView;
import com.bell.weatherbroker.model.Weather;
import com.bell.weatherbroker.repository.WeatherRepository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

@RequestScoped
public class WeatherServiceImpl implements WeatherService {

    private final WeatherRepository repository;
    private final Validator validator;

    @Inject
    public WeatherServiceImpl(WeatherRepository repository, Validator validator) {
        this.repository = repository;
        this.validator = validator;
    }

    @Override
    public void add(WeatherView weatherView) {
        Weather model = new Weather();
        model.setCityName(weatherView.getCity());
        model.setCountryName(weatherView.getCountry());
        model.setWindSpeed(weatherView.getWindSpeed());
        model.setCondition(weatherView.getCondition());
        model.setTemperature(weatherView.getTemperature());

        StringBuilder message = new StringBuilder();

        Set<ConstraintViolation<Weather>> validate = validator.validate(model);
        if (!validate.isEmpty()) {
            for (ConstraintViolation<Weather> violation : validate) {
                message.append(violation.getMessage());
                message.append("; ");
            }
        }
        if (message.length() > 0) {
            throw new RuntimeException("Ошибка валидации "+message.toString().trim());
        }
        repository.add(model);
    }

    @Override
    public WeatherView getWeather(String cityName) {
        Weather model = repository.findByCityName(cityName);
        WeatherView weatherView = new WeatherView();
        weatherView.setCity(model.getCityName());
        weatherView.setCountry(model.getCountryName());
        weatherView.setWindSpeed(model.getWindSpeed());
        weatherView.setCondition(model.getCondition());
        weatherView.setTemperature(model.getTemperature());

        return weatherView;
    }

}
