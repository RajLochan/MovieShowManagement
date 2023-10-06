package mbp.booking.domainObject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class CartBillDetail implements Serializable {
    private double movieBasePrice;
    private double payableAmount;
    private double discountAmount;
    private double taxAmount;
    private double beforeTaxAmount;
    private PromoDetail promoDetail;

}
