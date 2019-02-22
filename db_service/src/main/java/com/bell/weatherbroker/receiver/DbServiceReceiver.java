package com.bell.weatherbroker.receiver;

import bell.commonmodel.model.WeatherView;
import com.bell.weatherbroker.service.WeatherService;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

@MessageDriven(name = "DbServiceReceiver", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup",
                propertyValue = "java:jboss/exported/jms/WeatherToDBQueue"),
        @ActivationConfigProperty(propertyName = "destinationType",
                propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode",
                propertyValue = "Auto-acknowledge")})

public class DbServiceReceiver implements MessageListener {

    private WeatherService service;

    @Inject
    public void setService(WeatherService service) {
        this.service = service;
    }

    public DbServiceReceiver() {
    }

    public void onMessage(Message rcvMessage) {
        try {
            WeatherView weatherView = rcvMessage.getBody(WeatherView.class);
            service.add(weatherView);
        } catch (JMSException e) {
            throw new RuntimeException("Ошибка при чтении сообщения из WeatherToDBQueue: ", e);
        }
    }

}
