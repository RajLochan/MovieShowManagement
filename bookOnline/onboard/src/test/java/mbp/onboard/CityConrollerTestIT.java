package mbp.onboard;

import com.fasterxml.jackson.databind.ObjectMapper;

import mbp.onboard.domainObject.CityResponse;
import mbp.onboard.domainObject.CityUpsertRequest;
import mbp.onboard.entity.City;
import mbp.onboard.entity.Country;
import mbp.onboard.repo.CityRepository;
import mbp.onboard.repo.CountryRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.CoreMatchers.is;

@SpringBootTest

@AutoConfigureMockMvc
class CityConrollerTestIT {



    @Autowired
    private MockMvc mockMvc;

     @Value("${mbp.onboard.API_KEY}")
     private String API_KEY;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private CountryRepository countryRepository;

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {

    }



    @Test
    void saveCity() throws Exception {
        CityUpsertRequest request = new CityUpsertRequest("HighTech", "Delhi", "Delhi", 1);
        ResultActions response = mockMvc.perform(post("/onboard/v1/cities/").header("X-API-Key", API_KEY)
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(request)));

        response.andDo(print()).
                andExpect(status().isCreated())
                .andExpect(jsonPath("$.name",
                        is(request.getName()))).
                andExpect(jsonPath("$.district",
                        is(request.getDistrict()))).
                andExpect(jsonPath("$.state",
                        is(request.getState()))).
                andExpect(jsonPath("$.countryId",
                        is(request.getCountryId()))).
                andExpect(jsonPath("$.id").exists()).
                andDo(res -> {
                    //clean up
                    CityResponse city = objectMapper.readValue(res.getResponse().getContentAsString(),
                            CityResponse.class);
                    cityRepository.deleteById(city.getId());
                });

    }


    @Test
    void getCity() throws Exception {
        Country country = countryRepository.save(new Country(null, "testCountry", "98"));
        City city = cityRepository.save(new City(null, "Hightec", "delhi", "delhi", country));
        ResultActions response = mockMvc.perform(get("/onboard/v1/cities/{id}", city.getId()).header("X-API-Key", API_KEY));
        response.andDo(print()).
                andExpect(status().isOk())
                .andExpect(jsonPath("$.name",
                        is(city.getName()))).
                andExpect(jsonPath("$.district",
                        is(city.getDistrict()))).
                andExpect(jsonPath("$.state",
                        is(city.getState()))).
                andExpect(jsonPath("$.countryId",
                        is(city.getCountry().getId()))).
                andExpect(jsonPath("$.id",
                        is(city.getId())));

        cityRepository.deleteById(city.getId());
        countryRepository.deleteById(country.getId());
    }

    @Test
    void getAllCities() {
    }

    @Test
    void updateCity() throws Exception {
        Country country = countryRepository.save(new Country(null, "testCountry", "98"));
        City city = cityRepository.save(new City(null, "Hightec", "delhi", "delhi", country));

        CityUpsertRequest request = new CityUpsertRequest("SuperTech", "Delhi", "Delhi", country.getId());
        ResultActions response = mockMvc.perform(put("/onboard/v1/cities/{id}",city.getId()).header("X-API-Key", API_KEY)
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(request)));

        response.andDo(print()).
                andExpect(status().isOk())
                .andExpect(jsonPath("$.name",
                        is(request.getName()))).
                andExpect(jsonPath("$.district",
                        is(request.getDistrict()))).
                andExpect(jsonPath("$.state",
                        is(request.getState()))).
                andExpect(jsonPath("$.countryId",
                        is(request.getCountryId()))).
                andExpect(jsonPath("$.id",
                        is(city.getId())));

        cityRepository.deleteById(city.getId());
        countryRepository.deleteById(country.getId());
    }

    @Test
    void deleteCity() throws Exception {
        Country country = countryRepository.save(new Country(null, "testCountry", "98"));
        City city = cityRepository.save(new City(null, "Hightec", "delhi", "delhi", country));
        ResultActions response = mockMvc.perform(delete("/onboard/v1/cities/{id}", city.getId()).header("X-API-Key", API_KEY));
        response.andDo(print()).
                andExpect(status().isOk());

        countryRepository.deleteById(country.getId());
    }
}