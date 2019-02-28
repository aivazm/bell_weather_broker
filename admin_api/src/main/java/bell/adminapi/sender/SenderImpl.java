package bell.adminapi.sender;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.Queue;

/**
 * {@inheritDoc}
 */
@Slf4j
@RequestScoped
public class SenderImpl implements Sender {

    private Queue queue;
    private JMSContext context;

    @Resource(mappedName = "java:jboss/exported/jms/CityWeatherQueue")
    public void setQueue(Queue queue) {
        this.queue = queue;
    }

    @Inject
    public SenderImpl(JMSContext context) {
        this.context = context;
    }

    public SenderImpl() {
    }

    /**
     * {@inheritDoc}
     */
    public void sendMessage(String txt) {
        if (StringUtils.isBlank(txt)) {
            throw new RuntimeException("Parameter txt is empty");
        }
        context.createProducer().send(queue, txt);
    }
}