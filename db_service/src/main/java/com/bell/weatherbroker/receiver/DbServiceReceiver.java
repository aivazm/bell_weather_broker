package com.bell.weatherbroker.receiver;

import bell.commonmodel.model.WeatherView;
import com.bell.weatherbroker.service.WeatherService;
import lombok.extern.slf4j.Slf4j;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * Слушатель сообщений из очереди WeatherToDBQueue
 */

@MessageDriven(name = "DbServiceReceiver", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup",
                propertyValue = "java:jboss/exported/jms/WeatherToDBQueue"),
        @ActivationConfigProperty(propertyName = "destinationType",
                propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode",
                propertyValue = "Auto-acknowledge")})
@Slf4j
public class DbServiceReceiver implements MessageListener {

    private final WeatherService service;

    @Inject
    public DbServiceReceiver(WeatherService service) {
        this.service = service;
    }

    public DbServiceReceiver() {
        service = null;
    }

    /**
     * Метод принимает сообщение rcvMessage и направляет в сервис
     * @param rcvMessage
     */
    public void onMessage(Message rcvMessage) {
        try {
            WeatherView weatherView = rcvMessage.getBody(WeatherView.class);
            service.add(weatherView);
        } catch (JMSException e) {
            log.warn("Error while get body from MQ", rcvMessage, e);
            throw new RuntimeException("Error while get body from MQ WeatherToDBQueue: ", e);
        }
    }

}
