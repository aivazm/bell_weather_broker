package bell.yahooweather.receiver;


import bell.commonmodel.model.WeatherView;
import bell.yahooweather.sender.Sender;
import bell.yahooweather.service.YahooService;
import org.junit.Before;
import org.junit.Test;

import javax.jms.JMSException;
import javax.jms.Message;

import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ReceiverYahooTest {

    private final YahooService yahooService = mock(YahooService.class);
    private final Sender sender = mock(Sender.class);
    private final Message message = mock(Message.class);
    private static final String CITY = "cityName";
    private WeatherView view;

    private ReceiverYahoo receiver;

    @Before
    public void setUp() throws Exception {
        receiver=new ReceiverYahoo(yahooService,sender);
        view = WeatherView.builder()
                .city("city")
                .country("country")
                .condition("condition")
                .windSpeed(1.0)
                .temperature(10)
                .build();
        when(message.getBody(String.class)).thenReturn(CITY);
        when(yahooService.getWeatherFromYahoo(CITY)).thenReturn(view);
    }

    @Test
    public void onMessage() throws JMSException {
        receiver.onMessage(message);
        verify(message).getBody(String.class);
        verify(sender).sendMessage(view);
    }

    @Test(expected = RuntimeException.class)
    public void onMessageJmsException() throws JMSException {
        when(message.getBody(String.class)).thenThrow(JMSException.class);
        receiver.onMessage(message);
    }

    @Test(expected = RuntimeException.class)
    public void onMessageIoException() throws IOException {
        when(yahooService.getWeatherFromYahoo(CITY)).thenThrow(IOException.class);
        receiver.onMessage(message);
    }
}