package bell.yahooweather.receiver;

import bell.commonmodel.model.WeatherView;
import bell.yahooweather.sender.SenderMessages;
import bell.yahooweather.service.YahooService;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import java.io.IOException;

@MessageDriven(name = "ReceiverYahoo", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup",
                propertyValue = "java:jboss/exported/jms/CityWeatherQueue"),
        @ActivationConfigProperty(propertyName = "destinationType",
                propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode",
                propertyValue = "Auto-acknowledge")})

public class ReceiverYahoo implements MessageListener {


    private YahooService yahooService;

    private SenderMessages senderMessages;

    @Inject
    public void setYahooService(YahooService yahooService) {
        this.yahooService = yahooService;
    }

    @Inject
    public void setSenderMessages(SenderMessages senderMessages) {
        this.senderMessages = senderMessages;
    }

    public void onMessage(Message rcvMessage) {
        try {
            String cityName = rcvMessage.getBody(String.class);
            WeatherView weatherView = yahooService.getWeatherFromYahoo(cityName);
            if (weatherView != null) {
                senderMessages.sendMessage(weatherView);
            }
        } catch (JMSException | IOException e) {
            throw new RuntimeException("Ошибка при чтении сообщения из CityWeatherQueue: ", e);
        }
    }


}
