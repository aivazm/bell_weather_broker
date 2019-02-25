package com.bell.weatherbroker.controller;

import bell.commonmodel.model.WeatherView;
import bell.commonmodel.remote.WeatherTransmitter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.Model;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class WeatherServiceControllerTest {

    private final Model model = mock(Model.class);
    private final WeatherTransmitter transmitter = mock(WeatherTransmitter.class);
    private static final String CITY = "cityName";
    private WeatherView view;

    private WeatherServiceController controller;

    @Before
    public void setUp() {
        controller = new WeatherServiceController(transmitter);
        view = WeatherView.builder()
                .city("city")
                .country("country")
                .condition("condition")
                .windSpeed(1.0)
                .temperature(10)
                .build();
        when(transmitter.getWeather(CITY.toLowerCase())).thenReturn(view);
    }

    @Test
    public void getWeatherJson() {
        String s = controller.getWeatherJson(model,CITY);
        verify(model).addAttribute(CITY,view);
        Assert.assertNotNull(s);
    }

    @Test(expected = RuntimeException.class)
    public void getWeatherJsonException() {
        when(transmitter.getWeather(CITY.toLowerCase())).thenReturn(null);
        controller.getWeatherJson(model,CITY);
    }
}