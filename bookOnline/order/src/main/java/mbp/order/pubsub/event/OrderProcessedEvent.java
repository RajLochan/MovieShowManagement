package mbp.order.pubsub.event;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.time.LocalTime;

@Data
public class OrderProcessedEvent extends OrderCreateEvent {
    @NotEmpty
    private LocalTime oderProcessedTime;

}
