package bell.adminapi;

import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.Queue;

@RequestScoped
public class Sender {
    @Resource(mappedName = "java:jboss/exported/jms/CityWeatherQueue")
    private Queue queue;

    @Inject
    private JMSContext context;


    public void sendMessage(String txt) {
        context.createProducer().send(queue, txt);
    }
}