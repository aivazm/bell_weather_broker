package com.bell.weatherbroker.service;

import bell.commonmodel.model.WeatherView;
import com.bell.weatherbroker.model.Weather;
import com.bell.weatherbroker.repository.WeatherRepository;
import org.junit.Before;
import org.junit.Test;

import javax.validation.Validator;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class WeatherServiceImplTest {

    private final WeatherRepository repository = mock(WeatherRepository.class);
    private final Validator validator = mock(Validator.class);
    private static final String CITY = "cityName";

    private Weather model;
    private WeatherView view;
    private WeatherServiceImpl service;

    @Before
    public void setUp() {
        view = WeatherView.builder()
                .city("city")
                .country("country")
                .condition("condition")
                .windSpeed(1.0)
                .temperature(10)
                .build();
        model = Weather.builder()
                .cityName(view.getCity())
                .countryName(view.getCountry())
                .condition(view.getCondition())
                .windSpeed(view.getWindSpeed())
                .temperature(view.getTemperature())
                .build();

        service = new WeatherServiceImpl(repository,validator);
    }

    @Test
    public void add() {
        service.add(view);
        verify(validator).validate(model);
        verify(repository).add(model);
    }

    @Test(expected = RuntimeException.class)
    public void addException() {
        when(validator.validate(model)).thenThrow(RuntimeException.class);
        service.add(view);
        verify(repository,times(0)).add(model);
    }


    @Test
    public void getWeather() {
        service.getWeather(CITY);
        verify(repository).findByCityName(CITY);
    }
}