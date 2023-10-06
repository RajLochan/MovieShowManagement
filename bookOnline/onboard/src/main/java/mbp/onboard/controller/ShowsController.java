package mbp.onboard.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import mbp.onboard.domainObject.ShowResponse;
import mbp.onboard.domainObject.ShowUpsertRequest;
import mbp.onboard.service.ShowsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/onboard/v1/shows")
@Slf4j
@CrossOrigin
@Tag(name = "onboard-service")
public class ShowsController {

    @Autowired
    private ShowsService showsService;

    @Operation(summary = "add new show")
    @PostMapping(value = "/", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ShowResponse> save(@Valid @RequestBody ShowUpsertRequest request) throws Exception {
        log.info("add new show request body={}", request);
        ShowResponse response = showsService.saveShow(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "get city using id")
    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ShowResponse> get(@PathVariable Integer id) throws Exception {
        log.info("get show request id={}", id);
        ShowResponse response = showsService.getShow(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "update existing show")
    @PutMapping(value = "/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces =
            {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ShowResponse> update(@PathVariable Integer id, @RequestBody ShowUpsertRequest request) throws Exception {
        log.info("update show request id={}, body={}", id, request);
        ShowResponse response = showsService.updateShow(id, request);
        return ResponseEntity.ok(response);
    }


    @Operation(summary = "delete show using id")
    @DeleteMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity delete(@PathVariable Integer id) throws Exception {
        log.info("delete show request id={}", id);
        showsService.deleteShow(id);
        return ResponseEntity.ok(null);
    }

    @Operation(summary = "get free seats count using id")
    @GetMapping(value = "/{id}/free-seats", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Integer> getFreeSeatsCount(@PathVariable Integer id) throws Exception {
        log.info("get free seats count request id={}", id);
        Integer count = showsService.getFreeSeatsCount(id);
        return ResponseEntity.ok(count);
    }



}
