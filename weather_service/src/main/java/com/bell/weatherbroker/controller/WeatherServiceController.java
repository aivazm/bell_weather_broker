package com.bell.weatherbroker.controller;

import bell.commonmodel.model.WeatherView;
import bell.commonmodel.remote.WeatherTransmitter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Контроллер обработки запросов данных о погоде
 */
@Slf4j
@Controller
@RequestMapping(value = "/api", produces = APPLICATION_JSON_VALUE)
public class WeatherServiceController {

    private final WeatherTransmitter transmitter;

    @Autowired
    public WeatherServiceController(WeatherTransmitter transmitter) {
        this.transmitter = transmitter;
    }

    /**
     * Метод получает данные о погоде по cityName и добавляет в model.
     *
     * @param model
     * @param cityName
     * @return
     */
    @GetMapping(value = "/weather")
    public String getWeatherJson(Model model, @RequestParam String cityName) {
        String result = null;
        if (model == null || StringUtils.isBlank(cityName)) {
            log.info("One or more parameters is null or empty");
        } else {
            model.addAttribute(cityName, getWeatherView(cityName));
            result = "jsonTemplate";
        }
        return result;
    }

    private WeatherView getWeatherView(String cityName) {
        WeatherView weatherView = transmitter.getWeather(cityName.toLowerCase());
        if (weatherView == null) {
            log.warn("City " + cityName + " not found");
        }
        return weatherView;
    }


}


