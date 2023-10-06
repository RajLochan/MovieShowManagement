package mbp;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import mbp.Entity.MovieTheaterViewEntity;
import mbp.domainobject.SearchFilterRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SearchShowControllerTestIT {
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
    private Date getDate(){
        Calendar calendar = Calendar.getInstance();

        // Set the month to October (0-based, so 9 represents October)
        calendar.set(Calendar.MONTH, Calendar.OCTOBER);

        // Set the day of the month to 1
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        // Get the Date object representing October 1st of the current year
        Date date = calendar.getTime();
        return date;
    }

    @Test
    void getShowsByDate() throws Exception {
        SearchFilterRequest request=new SearchFilterRequest();
        request.setShowDate(getDate());
        request.setCityName("Bengaluru");
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/search/v1/theatres/")
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(request)));
        MvcResult result = response.andDo(print()).andExpect(status().isOk()).andReturn();
        List<MovieTheaterViewEntity> theatreResponse = objectMapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<List<MovieTheaterViewEntity>>() {});
        System.out.println(theatreResponse.size());
        assertNotNull(theatreResponse);
    }

    @Test
    void getRunningShows() throws Exception {
        SearchFilterRequest request=new SearchFilterRequest();
        request.setShowDate(getDate());
        request.setCityName("Bengaluru");
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/search/v1/theatres/runningShows")
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(request)));
        MvcResult result = response.andDo(print()).andExpect(status().isOk()).andReturn();
        List<MovieTheaterViewEntity> theatreResponse = objectMapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<List<MovieTheaterViewEntity>>() {});
        System.out.println(theatreResponse.size());
        assertNotNull(theatreResponse);
    }

}
