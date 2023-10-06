package mbp.booking.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mbp.booking.util.Status;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "seats_allocation_status")
@IdClass(SeatAllocationStatusId.class)
public class SeatAllocationStatus {
    @Id
    @Column(name = "show_id")
    private Integer showId;

    @Id
    @Column(name = "seat_id")
    private Integer seatId;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "status")
    private Status status;


}
