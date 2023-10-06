package mbp.booking.pubsub;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import mbp.booking.pubsub.events.SeatLockEvent;
import mbp.booking.pubsub.events.SeatLockMessage;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class ShowsSeatLockPublisher  {
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    FanoutExchange fanoutExchange;
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    public ShowsSeatLockPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


    public void publish(String key, SeatLockEvent message) {
        ObjectMapper objectMapper = new ObjectMapper();
        String convertedString=null;
        try {
          //objectBytes = objectMapper.writeValueAsBytes(message);
            convertedString = convertObjectToJson(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        rabbitTemplate.convertAndSend(fanoutExchange.getName(),"",convertedString);
        // publish message to MQ i.e. shows.seats.publisher.topic
    }


    private String convertObjectToJson(Object object) {

        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }
}
