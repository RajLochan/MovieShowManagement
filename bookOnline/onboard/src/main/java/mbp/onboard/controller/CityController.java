package mbp.onboard.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import mbp.onboard.domainObject.CityResponse;
import mbp.onboard.domainObject.CityUpsertRequest;
import mbp.onboard.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/onboard/v1/cities")
@Slf4j
@CrossOrigin
@Tag(name = "onboard-svc")
public class CityController {

    @Autowired
    private CityService cityService;

    @Operation(summary = "add new city")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "New city saved successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Error occurred while processing")
    })
    @PostMapping(value = "/", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<CityResponse> save(@Valid @RequestBody CityUpsertRequest request) throws Exception {
        log.info("add new city request body={}", request);
        CityResponse response = cityService.saveCity(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "get city using id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Error occurred while processing")
    })
    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<CityResponse> get(@PathVariable Integer id) throws Exception {
        log.info("get city request id={}", id);
        CityResponse cityResponse = cityService.getCity(id);
        return ResponseEntity.ok(cityResponse);
    }

    @Operation(summary = "get all cities with pagination")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Error occurred while processing")
    })
    @GetMapping(value = "/", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<CityResponse>> getAll(@RequestParam(defaultValue = "0", required = false) Integer page,
                                                     @RequestParam(defaultValue = "5", required = false) Integer pageSize) throws Exception {
        log.info("get all cities request page={}, pageSize={}", page, pageSize);
        List<CityResponse> cities = cityService.getCities(page, pageSize);
        return ResponseEntity.ok(cities);
    }

    @Operation(summary = "update existing city")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "City updated successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Error occurred while processing")
    })
    @PutMapping(value = "/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces =
            {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<CityResponse> update(@PathVariable Integer id, @RequestBody CityUpsertRequest request) throws Exception {
        log.info("update city request id={}, body={}", id, request);
        CityResponse response = cityService.updateCity(id, request);
        return ResponseEntity.ok(response);
    }


    @Operation(summary = "delete city using id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Error occurred while processing")
    })
    @DeleteMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity delete(@PathVariable Integer id) throws Exception {
        log.info("delete city request id={}", id);
        cityService.deleteCity(id);
        return ResponseEntity.ok(null);
    }
}
