package com.example.backend.controller;

import com.example.backend.Beans.Customer;
import com.example.backend.Dtos.JwtAuthenticationRequest;
import com.example.backend.Dtos.UserTokenState;
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

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CottageOwnerControllerTests {
    private static final String CONTROLLER_URL = "/cottageOwner";
    private static final String LOGIN_URL = "/auth/login";

    private final MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype());

    private MockMvc mockMvc;
    private static final Gson gson = new Gson();

    @Autowired
    private WebApplicationContext webApplicationContext;



    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
    }

    //Student2
    @Test
    public void testGetOwnerCottages() throws Exception{
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        mockMvc.perform(get(CONTROLLER_URL+"/cottages/6").header("Authorization", authorization()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(2)));;

    }

    private String authorization() throws Exception {
        JwtAuthenticationRequest user = new JwtAuthenticationRequest();
        user.setUsername("teddyjj25@gmail.com");
        user.setPassword("123");

        String result = this.mockMvc.perform(post(LOGIN_URL).contentType(contentType)
                .content(gson.toJson(user))).andReturn().getResponse().getContentAsString();

        return "Bearer " + gson.fromJson(result, UserTokenState.class).getAccessToken();
    }
}
