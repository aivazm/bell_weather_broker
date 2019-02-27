package bell.yahooweather.receiver;

import bell.commonmodel.model.WeatherView;
import bell.yahooweather.sender.Sender;
import bell.yahooweather.service.YahooService;
import lombok.extern.slf4j.Slf4j;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import java.io.IOException;


/**
 * Слушатель сообщений из очереди CityWeatherQueue
 */
@MessageDriven(name = "ReceiverYahoo", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup",
                propertyValue = "java:jboss/exported/jms/CityWeatherQueue"),
        @ActivationConfigProperty(propertyName = "destinationType",
                propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode",
                propertyValue = "Auto-acknowledge")})

@Slf4j
public class ReceiverYahoo implements MessageListener {

    private YahooService yahooService;
    private Sender sender;

    @Inject
    public ReceiverYahoo(YahooService yahooService, Sender sender) {
        this.yahooService = yahooService;
        this.sender = sender;
    }

    public ReceiverYahoo() {
    }

    /**
     * Метод принимает мообщение rcvMessage.
     * Тело сообщения направляется в YahooService для получения WeatherView.
     * Объект WeatherView передается в Sender
     *
     * @param rcvMessage
     */
    public void onMessage(Message rcvMessage) {
        if (rcvMessage == null) {
            log.info("Parameter rcvMessage is null");
        } else {
            try {
                String cityName = rcvMessage.getBody(String.class);
                WeatherView weatherView = yahooService.getWeatherFromYahoo(cityName);
                if (weatherView != null) {
                    sender.sendMessage(weatherView);
                }
            } catch (JMSException | IOException e) {
                log.warn("Error while get body from MQ", rcvMessage, e);
                throw new RuntimeException("Error while get body from MQ CityWeatherQueue: ", e);
            }
        }
    }


}
