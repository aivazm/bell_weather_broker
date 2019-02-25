package com.bell.weatherbroker;

import com.bell.weatherbroker.service.WeatherService;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class WeatherTransmitterImplTest {

    private final WeatherService service = mock(WeatherService.class);
    private static final String CITY = "city";

    private WeatherTransmitterImpl transmitter;

    @Before
    public void setUp() throws Exception {
        transmitter = new WeatherTransmitterImpl(service);
    }

    @Test
    public void getWeather() {
        transmitter.getWeather(CITY);
        verify(service).getWeather(CITY);
    }
}