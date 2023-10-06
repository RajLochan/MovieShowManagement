package mbp.onboard.pubsub;


import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TheatresPublisher {
    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void publish(String key, TheatreMessage message) {
        // publish message to MQ i.e. theatres.publisher.topic
    }
}
