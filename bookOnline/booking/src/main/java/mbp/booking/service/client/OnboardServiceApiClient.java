package mbp.booking.service.client;

import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "OnboardServiceApiClient",fallback = OnboardFallback.class, path = "/onboard/v1/shows", url = "http://localhost:9090")
public interface OnboardServiceApiClient{
    @GetMapping("/{id}/free-seats")
    Integer getFreeSeatsCount(@RequestHeader("X-API-Key") String apiKey, @PathVariable("id") Integer showId);
}
