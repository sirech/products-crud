package com.hceris.crud.products;

import com.hceris.crud.Utils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductsControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void softDelete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(String.format("/rest/products/%d", 1L))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void create() throws Exception {
        String rawForm = Utils.getResourceFileAsString("create_form.json");
        mockMvc.perform(MockMvcRequestBuilders.post("/rest/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(rawForm))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }
}
