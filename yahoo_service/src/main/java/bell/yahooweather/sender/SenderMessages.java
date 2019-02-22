package bell.yahooweather.sender;

import bell.commonmodel.model.WeatherView;

import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.Queue;
import java.util.logging.Logger;

@RequestScoped
public class SenderMessages {
    private static Logger log = Logger.getLogger(SenderMessages.class.getName());

    @Resource(mappedName = "java:jboss/exported/jms/WeatherToDBQueue")
    private Queue queue;

    private JMSContext context;

    @Inject
    public SenderMessages(JMSContext context) {
        this.context = context;
    }

    public SenderMessages() {
    }

    public void sendMessage(WeatherView weatherView) {
        log.info("Направляем в очередь WeatherToDBQueue объект WeatherView: " + weatherView.getCity());
        context.createProducer().send(queue, weatherView);
    }
}
