package mbp.booking.pubsub.events;

import lombok.Data;

import java.time.LocalTime;

@Data
public class OrderFailEvent extends OrderCreateEvent{
    private LocalTime oderFailTime;
}
