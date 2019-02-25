package bell.adminapi.sender;


import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Queue;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SenderImplTest {

    private JMSContext context = mock(JMSContext.class);

    private SenderImpl sender;

    private Queue queue = mock(Queue.class);

    private JMSProducer producer = mock(JMSProducer.class);

    private static final String MESSAGE = "MESSAGE";

    @Before
    public void setUp() {
        sender = new SenderImpl(context);
        sender.setQueue(queue);
        when(context.createProducer()).thenReturn(producer);
    }

    @Test
    public void sendMessage() {
        sender.sendMessage(MESSAGE);
        Mockito.verify(producer, Mockito.times(1)).send(queue, MESSAGE);
    }

    @Test
    public void sendEmptyMessage() {
        sender.sendMessage("");
        Mockito.verify(producer, Mockito.times(0)).send(queue, "");
    }
}
