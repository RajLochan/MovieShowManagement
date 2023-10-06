package mbp.order.service.client;

import jakarta.validation.Valid;
import mbp.order.domainobject.PaymentRequest;

import mbp.order.domainobject.PaymentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "paymentSvcClient", path = "/payment-service/v1/payments",
        url = "https://paymentgeateway", fallback = PaymentSvcFallBack.class)
public interface PaymentServiceApiClient {


    @PostMapping("/process")
    PaymentResponse process(@Valid @RequestBody PaymentRequest request);
}
