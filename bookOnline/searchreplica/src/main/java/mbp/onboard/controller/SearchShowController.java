package mbp.onboard.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;;

import mbp.Entity.MovieTheaterViewEntity;
import mbp.domainobject.SearchFilterRequest;
import mbp.search.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/search/v1/theatres")
@Slf4j
@CrossOrigin
@Tag(name = "search-service")
public class SearchShowController {
    @Autowired
    private SearchService searchService;

    @Value("${spring.cache.caches.myCache.ttl}")
    Long maxAge;

    @Operation(summary = "Return list of theatres by city, lat/lng with pagination")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Error occurred while processing")
    })
    @GetMapping(value = "/", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<MovieTheaterViewEntity>> searchTheatres(@Valid @RequestBody SearchFilterRequest request) throws Exception {
        List<MovieTheaterViewEntity> results = searchService.theatreSearch(request);
        return ResponseEntity.ok().cacheControl(CacheControl.maxAge(maxAge, TimeUnit.SECONDS)).body(results);
    }

    @GetMapping(value = "/runningShows", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<MovieTheaterViewEntity>> searchRunningShows(@Valid @RequestBody SearchFilterRequest request) throws Exception {
        List<MovieTheaterViewEntity> results = searchService.searchRunningShows(request);
        return ResponseEntity.ok().cacheControl(CacheControl.maxAge(maxAge, TimeUnit.SECONDS)).body(results);
    }

}
