package mbp.booking.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@EqualsAndHashCode
public class SeatAllocationStatusId implements Serializable {
    private Integer showId;
    private Integer seatId;
}
