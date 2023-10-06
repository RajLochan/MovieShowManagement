package mbp.order.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mbp.order.util.OrderAction;
import mbp.order.util.Status;

import java.math.BigInteger;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private BigInteger id;

    @OneToOne
    @JoinColumn(name = "ordr_dtl_id", referencedColumnName = "id")
    private OrderDetail orderDetail;

    @Column(name = "last_action")
    private OrderAction lastAction;

    @Column(name = "order_payment_id")
    private String orderPaymentId;

    /*0-pending,1-processing, 2-success, 3-failed*/
    @Column(name = "status")
    private Status status= Status.pending;

    @Column(name = "reason")
    private String reason;

    @Column(name = "created_time")
    private LocalTime created= LocalTime.now();

    @Column(name = "updated_time")
    private LocalTime updated=LocalTime.now();

}
