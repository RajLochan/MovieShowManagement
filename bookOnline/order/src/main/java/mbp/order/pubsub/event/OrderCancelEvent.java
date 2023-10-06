package mbp.order.pubsub.event;

import lombok.Data;

import java.time.LocalTime;
@Data
public class OrderCancelEvent extends OrderCreateEvent{
    private LocalTime oderCancelTime;
}
