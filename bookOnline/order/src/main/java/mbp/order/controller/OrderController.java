package mbp.order.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import mbp.order.domainobject.OrderCreateRequest;
import mbp.order.domainobject.OrderCreateResponse;
import mbp.order.service.OrderService;
import mbp.order.util.OrderAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

@RestController
@RequestMapping("/order/v1/orders")
@Slf4j
@CrossOrigin
@Tag(name = "order-service")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Operation(summary = "create new order")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "New order saved successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Error occurred while processing")
    })
    @PostMapping(value = "/initiate", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces =
            {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<OrderCreateResponse> initiateOrder(@Valid @RequestBody OrderCreateRequest request) throws Exception {
        log.info("create new order request body={}", request);
        OrderCreateResponse response = orderService.createOrder(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "process order")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "request processed successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Error occurred while processing")
    })
    @PostMapping(value = "/updateAction/{orderId}/{newStatus}", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces =
            {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity  updateOrderAction(@PathVariable BigInteger orderId,
                                             @PathVariable OrderAction newStatus) throws IllegalStateException{
        log.info("order process request body={}", orderId);
        orderService.updateOrderAction(orderId,newStatus);
        return  ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

    @Operation(summary = "process order")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "request processed successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Error occurred while processing")
    })
    @PostMapping(value = "/cancelOrder/{orderId}", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces =
            {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity  cancelOrder(@PathVariable BigInteger orderId) throws IllegalStateException{
        log.info("order process request body={}", orderId);
        orderService.cancelOrder(orderId);
        return  ResponseEntity.ok(HttpStatus.ACCEPTED);
    }
}
