package com.bell.weatherbroker.controller;

import bell.commonmodel.model.WeatherView;
import bell.commonmodel.remote.WeatherTransmitter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Controller
@RequestMapping(value = "/api", produces = APPLICATION_JSON_VALUE)
public class WeatherServiceController {

    private final WeatherTransmitter transmitter;

    @Autowired
    public WeatherServiceController(WeatherTransmitter transmitter) {
        this.transmitter = transmitter;
    }

    @GetMapping(value = "/weather")
    public String getWeatherJson(Model model, @RequestParam String cityName) {
        model.addAttribute(cityName, getWeather(cityName));
        return "jsonTemplate";
    }

    private WeatherView getWeather(String cityName) {
        return transmitter.getWeather(cityName);
    }

}


