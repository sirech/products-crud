package com.hceris.crud.products;

import com.hceris.crud.Utils;
import com.hceris.crud.products.form.CreateForm;
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

    CreateForm form = new CreateForm("new_product", BigDecimal.valueOf(25));
    Product newProduct = new Product(4L, form.getName(), form.getPrice(), LocalDate.now(), false);

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
    public void createReturns400IfFormInvalid() throws Exception {
        String rawForm = Utils.getResourceFileAsString("invalid_create_form.json");
        when(repository.save(any(Product.class))).thenReturn(newProduct);

        mockMvc.perform(MockMvcRequestBuilders.post("/rest/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(rawForm))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void createCreatesANewProduct() throws Exception {
        String rawForm = Utils.getResourceFileAsString("create_form.json");
        when(repository.save(any(Product.class))).thenReturn(newProduct);

        mockMvc.perform(MockMvcRequestBuilders.post("/rest/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(rawForm))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string(newProduct.getId().toString()));
    }

    @Test
    public void updateReturns404IfProductDoesntExist() throws Exception {
        String rawForm = Utils.getResourceFileAsString("create_form.json");
        when(repository.findById(0L)).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.put("/rest/products/0")
                .contentType(MediaType.APPLICATION_JSON)
                .content(rawForm))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
