package com.bell.weatherbroker.configuration;

import bell.commonmodel.remote.WeatherTransmitter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.caucho.HessianProxyFactoryBean;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.BeanNameViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;


@Configuration
@ComponentScan("com.bell.weatherbroker.controller")
public class WebConfig {

    @Bean
    public HessianProxyFactoryBean exporter() {
        HessianProxyFactoryBean factory = new HessianProxyFactoryBean();
        factory.setServiceUrl("http://localhost:8080/db_service-1.0-SNAPSHOT/transmitter");
        factory.setServiceInterface(WeatherTransmitter.class);
        return factory;
    }

    @Bean
    public View jsonTemplate() {
        return new MappingJackson2JsonView();
    }

    @Bean
    public ViewResolver viewResolver() {
        return new BeanNameViewResolver();
    }

}
