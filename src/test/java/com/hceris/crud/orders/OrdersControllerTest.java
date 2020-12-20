package com.hceris.crud.orders;

import com.hceris.crud.config.JacksonConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;

import static org.mockito.Mockito.when;

@WebMvcTest
@ContextConfiguration(classes = {OrdersController.class, JacksonConfiguration.class})
class OrdersControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    OrdersRepository repository;

    Order order = new Order(1L, "consumer@gmail.com");
    Order secondOrder = new Order(2L, "consumer@gmail.com");

    @Test
    public void ordersReturnsAListOfOrders() throws Exception {
        when(repository.findAllByEmail(order.getEmail())).thenReturn(Arrays.asList(order, secondOrder));

        mockMvc.perform(MockMvcRequestBuilders.get("/rest/users/consumer%40gmail.com/orders")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].email").value("consumer@gmail.com"));
    }

}
