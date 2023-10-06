package mbp.onboard;

import com.fasterxml.jackson.databind.ObjectMapper;
import mbp.onboard.domainObject.ScreenResponse;
import mbp.onboard.domainObject.ScreenUpsertRequest;
import mbp.onboard.entity.Screen;
import mbp.onboard.entity.Theatre;
import mbp.onboard.repo.ScreenRepository;
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
class ScreenControllerTestIT {
    @Value("${mbp.onboard.API_KEY}")
    private String API_KEY;
    @Autowired
    private ScreenRepository ScreenRepository;
    @Autowired
    private TheatreRepository theatreRepository;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void saveScreen() throws Exception {
        ScreenUpsertRequest request = new ScreenUpsertRequest("ScreenTest", 50, 30, 10, 1);
        ResultActions response = mockMvc.perform(post("/onboard/v1/audies/").header("X-API-Key", API_KEY)
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(request)));
        MvcResult result = response.andDo(print()).andExpect(status().isCreated()).andReturn();
        ScreenResponse ScreenResponse = objectMapper.readValue(result.getResponse().getContentAsString(), ScreenResponse.class);
        assertNotNull(ScreenResponse);
        assertNotNull(ScreenResponse.getId());
        assertTrue(request.getName().equals(ScreenResponse.getName()));
        assertTrue(request.getTheatreId().equals(ScreenResponse.getTheatreId()));
        ScreenRepository.deleteById(ScreenResponse.getId());
    }

    @Test
    void getScreen() throws Exception {
        Theatre theatre = theatreRepository.findById(1).get();
        Screen Screen = new Screen(null,  50, 30, 10, "ScreenTest",theatre);
        Screen ScreenDb = ScreenRepository.save(Screen);
        ResultActions response = mockMvc.perform(get("/onboard/v1/audies/{id}", Screen.getId()).header("X-API-Key", API_KEY));
        MvcResult result = response.andDo(print()).andExpect(status().isOk()).andReturn();
        ScreenResponse ScreenResponse = objectMapper.readValue(result.getResponse().getContentAsString(), ScreenResponse.class);
        assertNotNull(ScreenResponse);
        assertTrue(ScreenDb.getId().equals(ScreenResponse.getId()));
        assertTrue(ScreenDb.getName().equals(ScreenResponse.getName()));
        assertTrue(ScreenDb.getTheatre().getId().equals(ScreenResponse.getTheatreId()));
        ScreenRepository.deleteById(ScreenResponse.getId());
    }

    @Test
    void updateScreen() throws Exception {
        Theatre theatre = theatreRepository.findById(1).get();
        Screen Screen = new Screen(null,  50, 30, 10,"ScreenTest",  theatre);
        Screen ScreenDb = ScreenRepository.save(Screen);
        ScreenUpsertRequest request = new ScreenUpsertRequest("ScreenTest", 50, 40, 10, 1);
        ResultActions response = mockMvc.perform(put("/onboard/v1/audies/{id}", ScreenDb.getId()).header("X-API-Key", API_KEY)
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(request)));
        MvcResult result = response.andDo(print()).andExpect(status().isOk()).andReturn();
        ScreenResponse ScreenResponse = objectMapper.readValue(result.getResponse().getContentAsString(), ScreenResponse.class);
        assertNotNull(ScreenResponse);
        assertTrue(ScreenDb.getId().equals(ScreenResponse.getId()));
        assertTrue(ScreenDb.getName().equals(ScreenResponse.getName()));
        assertTrue(ScreenDb.getTheatre().getId().equals(ScreenResponse.getTheatreId()));
        assertTrue(request.getMiddleSeats().equals(ScreenResponse.getMiddleSeats()));
        ScreenRepository.deleteById(ScreenResponse.getId());
    }

    @Test
    void deleteScreen() throws Exception {
        Theatre theatre = theatreRepository.findById(1).get();
        Screen Screen = new Screen(null,  50, 30, 10,"ScreenTest",  theatre);
        Screen ScreenDb = ScreenRepository.save(Screen);
        ResultActions response = mockMvc.perform(delete("/onboard/v1/audies/{id}", Screen.getId()).header("X-API-Key", API_KEY));
        response.andDo(print()).andExpect(status().isOk());
    }
}