package mbp.onboard.pubsub;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import mbp.onboard.service.ShowsService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ShowsSeatLockSubscriber {

    @Autowired
    private ShowsService showsService;
    @Autowired
    ObjectMapper objectMapper;

    @RabbitListener(queues = "${shows.seats.exchange.queue}")
    public void subscribe(String message) {
        //1. consume events 
        Optional<SeatConfirmEvent> deserializedObject = convertJsonToObject(message);
        //2. update movie_shows table
        try {
            //input: SeatLockMessage.java
            //showsService.updateShow(null, null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Optional<SeatConfirmEvent> convertJsonToObject(String message) {
        Optional<SeatConfirmEvent> myObject=Optional.empty();
        try {
            myObject =Optional.of(objectMapper.readValue(message, SeatConfirmEvent.class));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return myObject;
    }
}
