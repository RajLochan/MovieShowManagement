package mbp.onboard.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import mbp.onboard.domainObject.TheatreGetDeleteRequest;
import mbp.onboard.domainObject.TheatreOnboardRequest;
import mbp.onboard.domainObject.TheatreResponse;
import mbp.onboard.service.TheatreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/onboard/v1/theatres")
@Slf4j
@CrossOrigin
@Tag(name = "onboard-srv")
public class TheatreOnboardController {
    @Autowired
    private TheatreService theatreService;

    @Operation(summary = "add new theatre")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "added successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Error occurred while processing")
    })
    @PostMapping(value = "/", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<TheatreResponse> save(@Valid @RequestBody TheatreOnboardRequest request) throws Exception {
        log.info("add new theatre request body={}", request);
        TheatreResponse response = theatreService.saveTheatre(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "get theatre using id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Error occurred while processing")
    })
    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<TheatreResponse> get(@PathVariable Integer id,@Valid @RequestBody TheatreGetDeleteRequest request) throws Exception {
        log.info("get theatre request id={}", id);
        //validate the theater owner and then return the theatre
        TheatreResponse response = theatreService.getTheatre(id);
        return ResponseEntity.ok(response);
    }


    @Operation(summary = "update existing theatre")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Error occurred while processing")
    })
    @PutMapping(value = "/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces =
            {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<TheatreResponse> update(@PathVariable Integer id,@Valid @RequestBody TheatreOnboardRequest request) throws Exception {
        log.info("update theatre request id={}, body={}", id, request);
        TheatreResponse response = theatreService.updateTheatre(id, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "delete theatre using id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Error occurred while processing")
    })
    @DeleteMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity delete(@PathVariable Integer id, @Valid @RequestBody TheatreGetDeleteRequest request) throws Exception {
        log.info("delete theatre request id={}", id);
        //Verify the theater owner and then delete the theatre
        theatreService.deleteTheatre(id);
        return ResponseEntity.ok(null);
    }
}
