package mbp.onboard.pubsub;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import mbp.onboard.pubsub.events.SeatConfirmEvent;
import mbp.onboard.pubsub.events.SeatLockEvent;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SeatConfirmEventListener implements ChannelAwareMessageListener {
    @Autowired
    ObjectMapper objectMapper;



    @RabbitListener(queues = "${order_seat_confirm_queue}")
    public void onMessage(Message message, Channel channel) throws Exception {
        try {

            String payload = new String(message.getBody(), "UTF-8");
            Optional<SeatConfirmEvent> deserializedObject = convertJsonToObject(payload);
            // Process the Seat lock event
            //PROCESS INVENTORY CONFIRMATION
            // If processing is successful, acknowledge the message
            // create OderFailEvent in case of the failure
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            // Handle errors and optionally reject the message
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
        }
    }

    private Optional<SeatConfirmEvent> convertJsonToObject(String message) throws JsonProcessingException {
        Optional<SeatConfirmEvent> myObject=Optional.empty();
            myObject =Optional.of(objectMapper.readValue(message, SeatConfirmEvent.class));
        return myObject;
    }
}