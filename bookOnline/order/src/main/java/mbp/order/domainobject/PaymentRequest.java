package mbp.order.domainobject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PaymentRequest {
    private String transactionId;
    private String payerName;
    private String payerEmail;
    private double amount;
    private String currency;
    private String description;
}
