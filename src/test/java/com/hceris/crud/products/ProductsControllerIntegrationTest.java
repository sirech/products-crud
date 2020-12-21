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

/**
 * This tests are there to make sure the whole flow of the past is tested end to end.
 * <p>
 * We don't want to repeat testing conditions that have been tested in lower level tests. That's why it only checks for
 * the response codes of the routes.
 * </p>
 */
@SpringBootTest
@AutoConfigureMockMvc
public class ProductsControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void product() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(String.format("/rest/products/%d", 1L))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void products() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/rest/products")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void softDelete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(String.format("/rest/products/%d", 2L))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void create() throws Exception {
        String rawForm = Utils.getResourceFileAsString("product_form.json");
        mockMvc.perform(MockMvcRequestBuilders.post("/rest/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(rawForm))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void update() throws Exception {
        String rawForm = Utils.getResourceFileAsString("product_form.json");
        mockMvc.perform(MockMvcRequestBuilders.put(String.format("/rest/products/%d", 1L))
                .contentType(MediaType.APPLICATION_JSON)
                .content(rawForm))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
