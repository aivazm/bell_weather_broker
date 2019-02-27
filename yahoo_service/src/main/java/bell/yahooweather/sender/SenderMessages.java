package bell.yahooweather.sender;

import bell.commonmodel.model.WeatherView;

import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.Queue;

/**
 * {@inheritDoc}
 */
@RequestScoped
public class SenderMessages implements Sender {

    private Queue queue;
    private JMSContext context;

    @Resource(mappedName = "java:jboss/exported/jms/WeatherToDBQueue")
    public void setQueue(Queue queue) {
        this.queue = queue;
    }

    @Inject
    public SenderMessages(JMSContext context) {
        this.context = context;
    }

    public SenderMessages() {
    }

    /**
     * {@inheritDoc}
     */
    public void sendMessage(WeatherView weatherView) {
        if (weatherView != null) {
            context.createProducer().send(queue, weatherView);
        }
    }
}
