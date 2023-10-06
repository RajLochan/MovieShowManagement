package mbp.order;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Sets;


import mbp.order.domainobject.BookShowRequest;
import mbp.order.domainobject.BookShowResponse;
import mbp.order.pubsub.event.OrderCreateEvent;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.*;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestTemplate;


import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ComponentScan(basePackages = {"mbp.order.*"})
class OrderControllerTestIT {
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
    void createOrder() throws Exception{
       BookShowResponse saveCart=saveCart();
        OrderCreateEvent orderCreateEvent=new OrderCreateEvent();
        orderCreateEvent.setCartId(saveCart.getCartId());
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/order/v1/orders/initiate")
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(orderCreateEvent)));
        MvcResult result = response.andDo(print()).andExpect(status().isCreated()).andReturn();
     //   BookShowResponse BookShowResponse = objectMapper.readValue(result.getResponse().getContentAsString(), BookShowResponse.class);
        assertNotNull(result);
    }

    BookShowResponse saveCart() throws Exception {
        BookShowRequest request = new BookShowRequest("user1001", 1, Sets.newHashSet(21,22), 1, 1, 1);
        /*ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:9050/booking/v1/carts/")
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(request)));*/
         RestTemplate template = new RestTemplate();
        String url = "http://localhost:9050/booking/v1/carts/";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-API-Key","TestAPIKey");
        HttpEntity<BookShowRequest> requestEntity = new HttpEntity<>(request, headers);
        ResponseEntity<BookShowResponse> responseEntity = template.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                BookShowResponse.class
        );
        assertNotNull(responseEntity);
        System.out.println(responseEntity.getStatusCode());
        return responseEntity.getBody();
    /*    MvcResult result = response.andDo(print()).andExpect(status().isCreated()).andReturn();
        mbp.booking.domainObject.BookShowResponse BookShowResponse = objectMapper.readValue(result.getResponse().getContentAsString(), mbp.booking.domainObject.BookShowResponse.class);
        assertNotNull(BookShowResponse);
        assertNotNull(BookShowResponse.getCartId());
        System.out.println(BookShowResponse.getCartId());*/

    }
}