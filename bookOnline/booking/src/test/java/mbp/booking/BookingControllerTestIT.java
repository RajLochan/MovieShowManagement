package mbp.booking;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Sets;

import mbp.booking.domainObject.*;
import mbp.booking.redisCache.CartBookingCacheRepository;
import mbp.booking.util.Common;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Set;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ComponentScan(basePackages = {"mbp.booking.*"})
class BookingControllerTestIT {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CartBookingCacheRepository cartBookingCache;


    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void saveCart() throws Exception {
        BookShowRequest request = new BookShowRequest("user1001", 1, Sets.newHashSet(21,22), 1, 1, 1);
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/booking/v1/carts/")
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(request)));
        MvcResult result = response.andDo(print()).andExpect(status().isCreated()).andReturn();
        BookShowResponse BookShowResponse = objectMapper.readValue(result.getResponse().getContentAsString(), BookShowResponse.class);
        assertNotNull(BookShowResponse);
        assertNotNull(BookShowResponse.getCartId());
        System.out.println(BookShowResponse.getCartId());
       cartBookingCache.deleteBookingDetail(BookShowResponse.getCartId());
    }

    @Test
    void updateCart() throws Exception {
        CartShowDetail BookShowDetail = new CartShowDetail(Common.getCartId(), "user1001", 1, 1, 1, 1, Sets.newHashSet(22, 23), new CartBillDetail(),
                new UserDetail());
        cartBookingCache.saveBookingDetail(BookShowDetail.getCartId(), BookShowDetail);
        Set<Integer> newSeats = Sets.newHashSet(21,22);
        BookShowRequest request = new BookShowRequest("user1001", 1, newSeats, 1, 1, 1);
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/booking/v1/carts/{id}", BookShowDetail.getCartId())
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(request)));
        MvcResult result = response.andDo(print()).andExpect(status().isOk()).andReturn();
        BookShowResponse BookShowResponse = objectMapper.readValue(result.getResponse().getContentAsString(), BookShowResponse.class);
        assertNotNull(BookShowResponse);
        assertNotNull(BookShowResponse.getCartId());
        assertTrue(BookShowResponse.getSeats().equals(newSeats));
        cartBookingCache.deleteBookingDetail(BookShowDetail.getCartId());
    }

    @Test
    void confirmCart() throws Exception {
        CartShowDetail BookShowDetail = new CartShowDetail(Common.getCartId(), "user1001", 1, 1, 1, 1, Sets.newHashSet(22, 23), new CartBillDetail(),
                new UserDetail());
        cartBookingCache.saveBookingDetail(BookShowDetail.getCartId(), BookShowDetail);
        BookShowRequest request = new BookShowRequest("user1001", BookShowDetail.getCityId(), BookShowDetail.getSeats() , BookShowDetail.getAudiId(),
                BookShowDetail.getShowId(),BookShowDetail.getTheatreId());
        ResultActions response = mockMvc.perform(post("/booking/v1/carts/{id}/confirm", BookShowDetail.getCartId())
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(request)));
        MvcResult result = response.andDo(print()).andExpect(status().isOk()).andReturn();
        BookShowResponse BookShowResponse = objectMapper.readValue(result.getResponse().getContentAsString(), BookShowResponse.class);
        assertNotNull(BookShowResponse);
        assertNotNull(BookShowResponse.getCartId());
        cartBookingCache.deleteBookingDetail(BookShowDetail.getCartId());
    }

    @Test
    void getCart() throws Exception {
        CartShowDetail BookShowDetail = new CartShowDetail(Common.getCartId(), "user1001", 1, 1, 1, 1, Sets.newHashSet(22, 23), new CartBillDetail(),
                new UserDetail());
        cartBookingCache.saveBookingDetail(BookShowDetail.getCartId(), BookShowDetail);
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/booking/v1/carts/{id}", BookShowDetail.getCartId()));
        MvcResult result = response.andDo(print()).andExpect(status().isOk()).andReturn();
        BookShowResponse BookShowResponse = objectMapper.readValue(result.getResponse().getContentAsString(), BookShowResponse.class);
        assertNotNull(BookShowResponse);
        assertNotNull(BookShowResponse.getCartId());
        assertTrue(BookShowResponse.getCartId().equals(BookShowDetail.getCartId()));
        cartBookingCache.deleteBookingDetail(BookShowResponse.getCartId());
    }

    @Test
    void deleteCart() throws Exception {
        CartShowDetail BookShowDetail = new CartShowDetail(Common.getCartId(), "user1001", 1, 1, 1, 1, Sets.newHashSet(22, 23), new CartBillDetail(),
                new UserDetail());
        cartBookingCache.saveBookingDetail(BookShowDetail.getCartId(), BookShowDetail);
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.delete("/booking/v1/carts/{id}", BookShowDetail.getCartId()));
        response.andDo(print()).andExpect(status().isOk());
    }
}