package com.example.backend.controller;

import com.example.backend.Beans.Customer;
import com.example.backend.Dtos.JwtAuthenticationRequest;
import com.example.backend.Dtos.UserTokenState;
import com.example.backend.Services.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerControllerTests {
    private static final String CUSTOMER_URL = "/customer";
    private static final String LOGIN_URL = "/auth/login";

    private final MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype());

    private static final Gson gson = new Gson();
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private CustomerService customerService;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
    }

    //Student 1
    @Test
    @Transactional
    public void testUpdateCustomer() throws Exception{
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        Customer newCustomer = customerService.findCustomerById(3);
        newCustomer.setFirstname("Zoran");

        mockMvc.perform(put(CUSTOMER_URL)
                .header("Authorization", authorization())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ow.writeValueAsString(newCustomer)))
                        .andExpect(status().isCreated())
                        .andExpect(content().string(containsString("Zoran")));
    }

    private String authorization() throws Exception {
        JwtAuthenticationRequest user = new JwtAuthenticationRequest();
        user.setUsername("stole@naaci.com");
        user.setPassword("123");

        String result = this.mockMvc.perform(post(LOGIN_URL).contentType(contentType)
                .content(gson.toJson(user))).andReturn().getResponse().getContentAsString();

        return "Bearer " + gson.fromJson(result, UserTokenState.class).getAccessToken();
    }
}
