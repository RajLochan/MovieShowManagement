package mbp.onboard;

import com.fasterxml.jackson.databind.ObjectMapper;
import mbp.onboard.domainObject.MovieResponse;
import mbp.onboard.domainObject.MovieUpsertRequest;
import mbp.onboard.entity.Movie;
import mbp.onboard.entity.MovieVariant;
import mbp.onboard.repo.MovieRepository;
import mbp.onboard.repo.MovieVariantRepository;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MovieControllerTestIT {
    @Autowired
    private MockMvc mockMvc;
    @Value("${mbp.onboard.API_KEY}")
    private String API_KEY;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private MovieVariantRepository movieVariantRepository;


    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void saveMovie() throws Exception {
        MovieUpsertRequest request = new MovieUpsertRequest("Movietest", "testing the movie", 2, 30, 1);
        ResultActions response = mockMvc.perform(post("/onboard/v1/movies/").header("X-API-Key", API_KEY)
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(request)));
        MvcResult result = response.andDo(print()).andExpect(status().isCreated()).andReturn();
        MovieResponse movieResponse = objectMapper.readValue(result.getResponse().getContentAsString(), MovieResponse.class);
        assertNotNull(movieResponse);
        assertNotNull(movieResponse.getId());
        assertTrue(request.getName().equals(movieResponse.getName()));
        assertTrue(request.getVariantId().equals(movieResponse.getVariantId()));
        movieRepository.deleteById(movieResponse.getId());
    }

    @Test
    void getMovie() throws Exception {
        MovieVariant movieVariant = movieVariantRepository.findById(1).get();
        Movie movieDb = movieRepository.save(new Movie(null, "Movietest", "testing the movie", 2, 30, movieVariant));
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/onboard/v1/movies/{id}", movieDb.getId()).header("X-API-Key", API_KEY));
     //   ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/onboard/v1/cities/{id}", city.getId()));
        MvcResult result = response.andDo(print()).andExpect(status().isOk()).andReturn();
        MovieResponse movieResponse = objectMapper.readValue(result.getResponse().getContentAsString(), MovieResponse.class);
        assertNotNull(movieResponse);
        assertTrue(movieDb.getId().equals(movieResponse.getId()));
        assertTrue(movieDb.getName().equals(movieResponse.getName()));
        assertTrue(movieDb.getVariant().getId().equals(movieResponse.getVariantId()));
        movieRepository.deleteById(movieResponse.getId());
    }

    @Test
    void updateMovie() throws Exception {
        MovieVariant movieVariant = movieVariantRepository.findById(1).get();
        Movie movieDb = movieRepository.save(new Movie(null, "Movietest", "testing the movie", 2, 30, movieVariant));
        MovieUpsertRequest request = new MovieUpsertRequest("Movietest", "testing the movie", 3, 10, 1);
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/onboard/v1/movies/{id}", movieDb.getId()).header("X-API-Key", API_KEY)
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(request)));
        MvcResult result = response.andDo(print()).andExpect(status().isOk()).andReturn();
        MovieResponse movieResponse = objectMapper.readValue(result.getResponse().getContentAsString(), MovieResponse.class);
        assertNotNull(movieResponse);
        assertNotNull(movieResponse.getId());
        assertTrue(request.getName().equals(movieResponse.getName()));
        assertTrue(request.getVariantId().equals(movieResponse.getVariantId()));
        assertTrue(request.getDurationHour().equals(movieResponse.getDurationHour()));
        assertTrue(request.getDurationMint().equals(movieResponse.getDurationMint()));
        movieRepository.deleteById(movieResponse.getId());
    }

    @Test
    void deleteMovie() throws Exception {
        MovieVariant movieVariant = movieVariantRepository.findById(1).get();
        Movie movieDb = movieRepository.save(new Movie(null, "Movietest", "testing the movie", 2, 30, movieVariant));
        ResultActions response = mockMvc.perform(delete("/onboard/v1/movies/{id}", movieDb.getId()).header("X-API-Key", API_KEY));
        response.andDo(print()).andExpect(status().isOk());
    }
}