package mbp.order.pubsub;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import mbp.booking.BookingService;
import mbp.booking.domainObject.BookShowRequest;
import mbp.booking.domainObject.BookShowResponse;
import mbp.booking.pubsub.events.OrderCreateEvent;
import mbp.booking.pubsub.events.SeatLockEvent;
import mbp.booking.service.CartService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SeatLockEventListener implements ChannelAwareMessageListener {
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    CartService cartService;
    @Autowired
    BookingService bookingService;

    @RabbitListener(queues = "${order_seat_lock_queue}")
    public void onMessage(Message message, Channel channel) throws Exception {
        try {

            String payload = new String(message.getBody(), "UTF-8");
            Optional<SeatLockEvent> deserializedObject = convertJsonToObject(payload);
            // Process the Seat lock event
            //PROCESS PAYMENT --> SEAT CONFIRM EVENT
            // If processing is successful, acknowledge the message
            // create OderFailEvent in case of the failure
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            // Handle errors and optionally reject the message
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
        }
    }

    private Optional<SeatLockEvent> convertJsonToObject(String message) throws JsonProcessingException {
        Optional<SeatLockEvent> myObject=Optional.empty();
            myObject =Optional.of(objectMapper.readValue(message, SeatLockEvent.class));
        return myObject;
    }
}