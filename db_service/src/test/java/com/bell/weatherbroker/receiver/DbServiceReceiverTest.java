package com.bell.weatherbroker.receiver;

import bell.commonmodel.model.WeatherView;
import com.bell.weatherbroker.service.WeatherService;
import org.junit.Before;
import org.junit.Test;

import javax.jms.JMSException;
import javax.jms.Message;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DbServiceReceiverTest {

    private final Message message = mock(Message.class);
    private final WeatherService service = mock(WeatherService.class);
    private WeatherView view;

    private DbServiceReceiver receiver;

    @Before
    public void setUp() throws JMSException {
        view = WeatherView.builder()
                .city("city")
                .country("country")
                .condition("condition")
                .windSpeed(1.0)
                .temperature(10)
                .build();
        receiver = new DbServiceReceiver(service);
        when(message.getBody(WeatherView.class)).thenReturn(view);
    }

    @Test
    public void onMessage() {
        receiver.onMessage(message);

        verify(service).add(view);
    }

    @Test(expected = RuntimeException.class)
    public void onMessageException() throws JMSException {
        when(message.getBody(WeatherView.class)).thenThrow(JMSException.class);
        receiver.onMessage(message);

        verify(service, times(0)).add(view);
    }

}