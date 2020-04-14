package com.github.mrlalonde.hateoas;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import javax.transaction.Transactional;
import java.io.IOException;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest()
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@Transactional
@TestPropertySource("/test-application.yaml")
public class HateoasApplicationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testDefaultProperties() throws Exception {
        mockMvc.perform(get("/hello"))
                .andExpect(content().string("HELLO-HATEOAS"));
    }

    @Test
    public void testOverridenProperties() throws Exception {
        mockMvc.perform(get("/bye"))
                .andExpect(content().string("BYE-HATEOAS"));
    }

    @Test()
    public void testPostGet() throws Exception {
        var cust1 = givenCustomerFrom(mockMvc.perform(post("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(new Customer("Larry", "Google")))));

        var cust2 = givenCustomerFrom(mockMvc.perform(post("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(new Customer("Larry", "Google")))));

        mockMvc.perform(get("/customers/{id}", cust1.getCustomerId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId", is((int) cust1.getCustomerId())));

        mockMvc.perform(get("/customers/{id}", cust2.getCustomerId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId", is((int) cust2.getCustomerId())));
    }

    private Customer givenCustomerFrom(ResultActions resultActions) throws IOException {
        var resultContent = resultActions
                .andReturn()
                .getResponse()
                .getContentAsByteArray();

        return objectMapper.readValue(resultContent, Customer.class);
    }

}
