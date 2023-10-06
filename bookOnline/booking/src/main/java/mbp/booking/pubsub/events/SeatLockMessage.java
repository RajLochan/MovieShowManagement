package mbp.booking.pubsub.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import mbp.booking.pubsub.Operation;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SeatLockMessage {
    private Integer showId;
    /* no. of seats locked after cart confirmation */
    private Set<Integer> seatsLocked;
    /* no. of seats booked after payment confirmation */
    private Set<Integer> seatsConfirmed;
    private Operation operation;
}
