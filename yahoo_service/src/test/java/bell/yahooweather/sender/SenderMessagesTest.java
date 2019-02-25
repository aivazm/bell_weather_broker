package bell.yahooweather.sender;

import bell.commonmodel.model.WeatherView;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Queue;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SenderMessagesTest {

    private JMSContext context = mock(JMSContext.class);
    private Queue queue = mock(Queue.class);
    private JMSProducer producer = mock(JMSProducer.class);
    private WeatherView view;

    private SenderMessages sender;

    @Before
    public void setUp() {
        view = WeatherView.builder()
                .city("city")
                .country("country")
                .condition("condition")
                .windSpeed(1.0)
                .temperature(10)
                .build();
        sender = new SenderMessages(context);
        sender.setQueue(queue);
        when(context.createProducer()).thenReturn(producer);
    }

    @Test
    public void sendMessage() {
        sender.sendMessage(view);
        Mockito.verify(producer, Mockito.times(1)).send(queue, view);
    }
}