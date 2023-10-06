package mbp.booking.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import mbp.booking.domainObject.BookShowRequest;
import mbp.booking.domainObject.BookShowResponse;
import mbp.booking.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/booking/v1/carts")
@Slf4j
@CrossOrigin
@Tag(name = "booking-service")
public class BookingController {
    @Autowired
    private CartService cartService;


    @Operation(summary = "create new booking")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "New cart saved successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Error occurred while processing")
    })
    @PostMapping(value = "/", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<BookShowResponse> save(@Valid @RequestBody BookShowRequest request) throws Exception {
        log.info("add new cart request body={}", request);
        BookShowResponse response = cartService.saveCart(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "update existing booking")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cart updated successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Error occurred while processing")
    })
    @PutMapping(value = "/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces =
            {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<BookShowResponse> update(@PathVariable String id, @RequestBody BookShowRequest request) throws Exception {
        log.info("update cart request id={}, body={}", id, request);
        BookShowResponse response = cartService.updateCart(id, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "booking confirmation and persist the seats for the user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Seats blocked successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Error occurred while processing")
    })
    @PostMapping(value = "/{id}/confirm", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces =
            {MediaType.APPLICATION_JSON_VALUE})

    public ResponseEntity<BookShowResponse> confirm(@PathVariable String id,
                                                    @Valid @RequestBody BookShowRequest request) throws Exception {
        log.info("confirm cart request id={}, body={}", id, request);
        BookShowResponse cartResponse = cartService.confirmCart(id,request);
        return ResponseEntity.ok(cartResponse);
    }

    @Operation(summary = "lock the seats for the user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Seats blocked successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Error occurred while processing")
    })
    @PostMapping(value = "/{id}/lock", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces =
            {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<BookShowResponse> lockSeats(@PathVariable String id,
                                                    @Valid @RequestBody BookShowRequest request) throws Exception {
        log.info("confirm cart request id={}, body={}", id, request);
        BookShowResponse cartResponse = cartService.confirmCart(id,request);
        return ResponseEntity.ok(cartResponse);
    }

    @Operation(summary = "get booking using id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Error occurred while processing")
    })
    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<BookShowResponse> get(@PathVariable String id) throws Exception {
        log.info("get cart request id={}", id);
        BookShowResponse cartResponse = cartService.getCart(id);
        return ResponseEntity.ok(cartResponse);
    }

    @Operation(summary = "get booking using id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Error occurred while processing")
    })
    @GetMapping(value = "/getCart/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public BookShowResponse getCart(@PathVariable String id) throws Exception {
        log.info("get cart request id={}", id);
        BookShowResponse cartResponse = cartService.getCart(id);
        return cartResponse;
    }


    @Operation(summary = "delete booking using id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Error occurred while processing")
    })
    @DeleteMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity delete(@PathVariable String id) throws Exception {
        log.info("delete cart request id={}", id);
        cartService.deleteCart(id);
        return ResponseEntity.ok(null);
    }

}
