package mbp.order.pubsub;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import mbp.booking.pubsub.events.SeatLockMessage;
import mbp.order.pubsub.event.OrderCreateEvent;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class OrderEventPublisher {
    private final RabbitTemplate rabbitTemplate;

    @Value("${shows.seats.publisher.exchange}")
    private String exchange;

    @Value("${order.create.queue}")
    private String orderCreateQueue;

    @Value("${order.create.exchange}")
    private String orderCreateExchange;


    @Autowired
    public OrderEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Autowired
    ObjectMapper objectMapper;


 /*   @Autowired
    private Queue paymentProcessedQueue; // publish payment processed event to Queue

    @Autowired
    private Queue orderFullFilledEventQueue; // publish order fullfilled event to Queue*/


    public void publishOrderCreateEvent(OrderCreateEvent event) {
        String convertedString=null;
        try {
            convertedString = convertObjectToJson(event);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        rabbitTemplate.convertAndSend(orderCreateQueue,convertedString);
    }


    private String convertObjectToJson(Object object) {

        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }
}
