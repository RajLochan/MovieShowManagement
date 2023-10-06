package mbp.onboard;

import com.fasterxml.jackson.databind.ObjectMapper;
import mbp.onboard.domainObject.*;
import mbp.onboard.repo.*;
import mbp.onboard.entity.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.sql.Time;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ShowsControllerTestIT {
    @Value("${mbp.onboard.API_KEY}")
    private String API_KEY;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MovieShowsRepository showsRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private ScreenRepository ScreenRepository;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void saveMovieShow() throws Exception {
        ShowUpsertRequest request = new ShowUpsertRequest(1, 1, 0, 0, new Date());
        ResultActions response = mockMvc.perform(post("/onboard/v1/shows/").header("X-API-Key", API_KEY)
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(request)));
        MvcResult result = response.andDo(print()).andExpect(status().isCreated()).andReturn();
        ShowResponse showResponse = objectMapper.readValue(result.getResponse().getContentAsString(), ShowResponse.class);
        assertNotNull(showResponse);
        assertNotNull(showResponse.getShowId());
        assertTrue(showResponse.getMovieId().equals(request.getMovieId()));
        assertTrue(showResponse.getAudiId().equals(request.getAudiId()));
        assertTrue(showResponse.getShowDateTime().toString().equals(request.getShowDateTime().toString()));
        showsRepository.deleteById(showResponse.getShowId());
    }

    @Test
    void getMovieShow() throws Exception {
        Screen Screen = ScreenRepository.findById(1).get();
        Movie movie = movieRepository.findById(1).get();
        Date showDateTime = new Date();
        MovieShows movieShow = showsRepository.save(new MovieShows(null, movie, Screen, 0, 0, new Time(showDateTime.getTime()),
                new java.sql.Date(showDateTime.getTime())));
        ResultActions response = mockMvc.perform(get("/onboard/v1/shows/{id}", movieShow.getId()).header("X-API-Key", API_KEY));
        MvcResult result = response.andDo(print()).andExpect(status().isOk()).andReturn();
        ShowResponse showResponse = objectMapper.readValue(result.getResponse().getContentAsString(), ShowResponse.class);
        assertNotNull(showResponse);
        assertNotNull(showResponse.getShowId());
        assertTrue(showResponse.getShowId().equals(movieShow.getId()));
        assertTrue(showResponse.getMovieId().equals(movieShow.getMovie().getId()));
        assertTrue(showResponse.getAudiId().equals(movieShow.getAudi().getId()));
        showsRepository.deleteById(showResponse.getShowId());
    }

    @Test
    void updateMovieShow() throws Exception {
        Screen Screen = ScreenRepository.findById(1).get();
        Movie movie = movieRepository.findById(1).get();
        Date showDateTime = new Date();
        MovieShows movieShow = showsRepository.save(new MovieShows(null, movie, Screen, 0, 0, new Time(showDateTime.getTime()),
                new java.sql.Date(showDateTime.getTime())));
        ShowUpsertRequest request = new ShowUpsertRequest(1, 1, 12, 10, new Date());
        ResultActions response = mockMvc.perform(put("/onboard/v1/shows/{id}", movieShow.getId()).header("X-API-Key", API_KEY)
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(request)));
        MvcResult result = response.andDo(print()).andExpect(status().isOk()).andReturn();
        ShowResponse showResponse = objectMapper.readValue(result.getResponse().getContentAsString(), ShowResponse.class);
        assertNotNull(showResponse);
        assertNotNull(showResponse.getShowId());
        assertTrue(showResponse.getMovieId().equals(request.getMovieId()));
        assertTrue(showResponse.getAudiId().equals(request.getAudiId()));
        assertTrue(showResponse.getShowDateTime().toString().equals(request.getShowDateTime().toString()));
        assertTrue(showResponse.getBookedSeats().equals(request.getBookedSeats()));
        assertTrue(showResponse.getBlockedSeats().equals(request.getBlockedSeats()));
        showsRepository.deleteById(showResponse.getShowId());
    }

    @Test
    void deleteMovieShow() throws Exception {
        Screen Screen = ScreenRepository.findById(1).get();
        Movie movie = movieRepository.findById(1).get();
        Date showDateTime = new Date();
        MovieShows movieShow = showsRepository.save(new MovieShows(null, movie, Screen, 0, 0, new Time(showDateTime.getTime()),
                new java.sql.Date(showDateTime.getTime())));
        ResultActions response = mockMvc.perform(delete("/onboard/v1/shows/{id}", movieShow.getId()).header("X-API-Key", API_KEY));
        response.andDo(print()).andExpect(status().isOk());

    }

    @Test
    void getFreeSeatsCount() throws Exception {
        Screen Screen = ScreenRepository.findById(1).get();
        Movie movie = movieRepository.findById(1).get();
        Date showDateTime = new Date();
        MovieShows movieShow = showsRepository.save(new MovieShows(null, movie, Screen, 10, 30,
                new Time(showDateTime.getTime()),
                new java.sql.Date(showDateTime.getTime())));
        ResultActions response = mockMvc.perform(get("/onboard/v1/shows/{id}/free-seats", movieShow.getId()).header("X-API-Key", API_KEY));
        MvcResult result = response.andDo(print()).andExpect(status().isOk()).andReturn();
        Integer freeSeats = objectMapper.readValue(result.getResponse().getContentAsString(), Integer.class);
        assertNotNull(freeSeats);
        assertTrue(freeSeats == (Screen.getTotalSeats() - (movieShow.getBlockedSeats() + movieShow.getBookedSeats())));
        showsRepository.deleteById(movieShow.getId());
    }
}