package mbp.onboard;

import com.fasterxml.jackson.databind.ObjectMapper;

import mbp.onboard.domainObject.TheatreGetDeleteRequest;
import mbp.onboard.domainObject.TheatreOnboardRequest;
import mbp.onboard.domainObject.TheatreResponse;

import mbp.onboard.entity.City;
import mbp.onboard.entity.Theatre;
import mbp.onboard.repo.CityRepository;
import mbp.onboard.repo.TheatreRepository;
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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TheatreControllerTestIT {
    @Value("${mbp.onboard.API_KEY}")
    private String API_KEY;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private TheatreRepository theatreRepository;
    @Autowired
    private CityRepository cityRepository;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void saveTheatre() throws Exception {
        TheatreOnboardRequest request = new TheatreOnboardRequest("theatreOwner1","theatreTest","sector19, noida", 75.12, 34.12,   1);
        ResultActions response = mockMvc.perform(post("/onboard/v1/theatres/").header("X-API-Key", API_KEY)
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(request)));
        MvcResult result = response.andDo(print()).andExpect(status().isCreated()).andReturn();
        TheatreResponse theatreResponse = objectMapper.readValue(result.getResponse().getContentAsString(), TheatreResponse.class);
        assertNotNull(theatreResponse);
        assertNotNull(theatreResponse.getId());
        assertTrue(request.getName().equals(theatreResponse.getName()));
        assertTrue(request.getCityId().equals(theatreResponse.getCityId()));
        theatreRepository.deleteById(theatreResponse.getId());
    }

    @Test
    void getTheatre() throws Exception {
        City cityDb = cityRepository.findById(1).get();
        Theatre theatreDb = theatreRepository.save(new Theatre(null,"theatreOwner1", "theatreTest", 75.12, 34.12,  "sector19, noida"
                , cityDb));
        TheatreGetDeleteRequest request=new TheatreGetDeleteRequest("theatreOwner1",theatreDb.getId());
        ResultActions response = mockMvc.perform(get("/onboard/v1/theatres/{id}", theatreDb.getId()).header("X-API-Key", API_KEY)
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(request)));
        MvcResult result = response.andDo(print()).andExpect(status().isOk()).andReturn();
        TheatreResponse theatreResponse = objectMapper.readValue(result.getResponse().getContentAsString(), TheatreResponse.class);
        assertNotNull(theatreResponse);
        assertNotNull(theatreResponse.getId());
        assertTrue(theatreDb.getName().equals(theatreResponse.getName()));
        assertTrue(theatreDb.getCity().getId().equals(theatreResponse.getCityId()));
        theatreRepository.deleteById(theatreResponse.getId());
    }

    @Test
    void updateTheatre() throws Exception {
        City cityDb = cityRepository.findById(1).get();
        Theatre theatreDb = theatreRepository.save(new Theatre(null,"theatreOwner1", "theatreTest", 75.12, 34.12,  "sector19, noida"
                , cityDb));
        TheatreOnboardRequest request = new TheatreOnboardRequest("theatreOwner1","theatreTest",  "sector19, " +
                "noida", 75.12, 34.12
                , 1);
        ResultActions response = mockMvc.perform(put("/onboard/v1/theatres/{id}", theatreDb.getId()).header("X-API-Key", API_KEY)
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(request)));
        MvcResult result = response.andDo(print()).andExpect(status().isOk()).andReturn();
        TheatreResponse theatreResponse = objectMapper.readValue(result.getResponse().getContentAsString(), TheatreResponse.class);
        assertNotNull(theatreResponse);
        assertTrue(theatreDb.getId().equals(theatreResponse.getId()));
        assertTrue(request.getName().equals(theatreResponse.getName()));
        assertTrue(request.getCityId().equals(theatreResponse.getCityId()));
        theatreRepository.deleteById(theatreResponse.getId());
    }

    @Test
    void deleteTheatre() throws Exception {
        City cityDb = cityRepository.findById(1).get();
        Theatre theatreDb = theatreRepository.save(new Theatre(null,"theatreOwner1", "theatreTest", 75.12, 34.12,  "sector19, noida"
                , cityDb));
        TheatreGetDeleteRequest request=new TheatreGetDeleteRequest("theatreOwner1",theatreDb.getId());
        ResultActions response = mockMvc.perform(delete("/onboard/v1/theatres/{id}", theatreDb.getId()).header("X-API-Key", API_KEY)
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(request)));
        response.andDo(print()).andExpect(status().isOk());
    }
}