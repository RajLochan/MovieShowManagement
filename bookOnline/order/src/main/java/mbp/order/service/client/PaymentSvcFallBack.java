package mbp.order.service.client;

import mbp.order.domainobject.PaymentRequest;
import mbp.order.domainobject.PaymentResponse;


public class PaymentSvcFallBack implements PaymentServiceApiClient{
    @Override
    public PaymentResponse process(PaymentRequest request) {
        return null;
    }
}
