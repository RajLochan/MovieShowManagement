package mbp.onboard.controller;

import io.swagger.v3.oas.annotations.Operation;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import mbp.onboard.domainObject.ScreenResponse;
import mbp.onboard.domainObject.ScreenUpsertRequest;
import mbp.onboard.service.ScreenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/onboard/v1/audies")
@Slf4j
@CrossOrigin
@Tag(name = "onboard-service")
public class ScreenController {
    @Autowired
    private ScreenService audiService;

    @Operation(summary = "add new audi")
    @PostMapping(value = "/", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ScreenResponse> save(@Valid @RequestBody ScreenUpsertRequest request) throws Exception {
        log.info("add new audi request body={}", request);
        ScreenResponse response = audiService.saveAudi(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "get audi using id")
    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ScreenResponse> get(@PathVariable Integer id) throws Exception {
        log.info("get audi request id={}", id);
        ScreenResponse response = audiService.getAudi(id);
        return ResponseEntity.ok(response);
    }


    @Operation(summary = "update existing audi")
    @PutMapping(value = "/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces =
            {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ScreenResponse> update(@PathVariable Integer id, @RequestBody ScreenUpsertRequest request) throws Exception {
        log.info("update audi request id={}, body={}", id, request);
        ScreenResponse response = audiService.updateAudi(id, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "delete audi using id")
    @DeleteMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity delete(@PathVariable Integer id) throws Exception {
        log.info("delete audi request id={}", id);
        audiService.deleteAudi(id);
        return ResponseEntity.ok(null);
    }
}
