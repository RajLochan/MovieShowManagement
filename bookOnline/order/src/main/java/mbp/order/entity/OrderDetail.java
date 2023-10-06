package mbp.order.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_detail")
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private BigInteger id;

    @Column(name = "show_id")
    private Integer showId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "cart_id")
    private String cartId;

    @Column(name = "seats")
    private String seats;

    @Column(name = "promo_id")
    private Integer promoId;

    @Column(name = "total_amt")
    private Double totalAmt;

    @Column(name = "discount_amt")
    private Double discountAmt;

    @Column(name = "tax_amt")
    private Double taxAmt;

    @Column(name = "payable_amt")
    private Double payableAmt;

    @Column(name = "created")
    private Date created;

    @Column(name = "updated")
    private Date updated;
}
