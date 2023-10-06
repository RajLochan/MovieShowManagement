package mbp.order.pubsub.event;

import jakarta.validation.constraints.NotEmpty;

import java.time.LocalTime;

public class OrderFullFilledEvent extends OrderProcessedEvent{
    @NotEmpty
    private LocalTime oderFullFilledTime;
}
