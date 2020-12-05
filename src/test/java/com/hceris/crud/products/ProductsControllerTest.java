package com.hceris.crud.products;

import com.hceris.crud.Utils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;

@WebMvcTest
class ProductsControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    ProductsRepository repository;

    Product product = new Product(1L, "test_product", BigDecimal.valueOf(1), LocalDate.now(), false);
    Product other = new Product(2L, "second_product", BigDecimal.valueOf(10), LocalDate.now(), false);

    @Test
    public void productReturns404IfProductDoesntExist() throws Exception {
        when(repository.findById(0L)).thenReturn(Optional.empty());
        mockMvc.perform(MockMvcRequestBuilders.get("/rest/products/0")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void productReturns200IfProductExists() throws Exception {
        when(repository.findById(product.getId())).thenReturn(Optional.of(product));

        mockMvc.perform(MockMvcRequestBuilders.get(String.format("/rest/products/%d", product.getId()))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("test_product"));
    }

    @Test
    public void productsReturnsAListOfProducts() throws Exception {
        when(repository.findAll()).thenReturn(Arrays.asList(product, other));

        mockMvc.perform(MockMvcRequestBuilders.get("/rest/products")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("second_product"));
    }

    @Test
    public void softDeleteMarksAProductAsDeleted() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(String.format("/rest/products/%d", product.getId()))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        verify(repository, times(1)).softDelete(product.getId());
    }

    @Test
    public void createCreatesANewProduct() throws Exception {
        String form = Utils.getResourceFileAsString("create_form.json");

        mockMvc.perform(MockMvcRequestBuilders.post("/rest/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(form))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
