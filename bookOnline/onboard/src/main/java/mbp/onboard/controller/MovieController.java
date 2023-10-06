package mbp.onboard.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import mbp.onboard.domainObject.MovieResponse;
import mbp.onboard.domainObject.MovieUpsertRequest;
import mbp.onboard.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/onboard/v1/movies")
@Slf4j
@CrossOrigin
@Tag(name = "onboard-service")
public class MovieController {
    @Autowired
    private MovieService movieService;

    @Operation(summary = "add new movie")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "added successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Error occurred while processing")
    })
    @PostMapping(value = "/", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<MovieResponse> save(@Valid @RequestBody MovieUpsertRequest request) throws Exception {
        log.info("add new movie request body={}", request);
        MovieResponse response = movieService.saveMovie(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "get movie using id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Error occurred while processing")
    })
    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<MovieResponse> get(@PathVariable Integer id) throws Exception {
        log.info("get movie request id={}", id);
        MovieResponse response = movieService.getMovie(id);
        return ResponseEntity.ok(response);
    }


    @Operation(summary = "update existing movie")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Error occurred while processing")
    })
    @PutMapping(value = "/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces =
            {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<MovieResponse> update(@PathVariable Integer id, @RequestBody MovieUpsertRequest request) throws Exception {
        log.info("update movie request id={}, body={}", id, request);
        MovieResponse response = movieService.updateMovie(id, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "delete movie using id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Error occurred while processing")
    })
    @DeleteMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity delete(@PathVariable Integer id) throws Exception {
        log.info("delete movie request id={}", id);
        movieService.deleteMovie(id);
        return ResponseEntity.ok(null);
    }
}
