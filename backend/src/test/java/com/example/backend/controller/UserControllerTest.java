package com.example.backend.controller;

import com.example.backend.Beans.User;
import com.example.backend.Dtos.JwtAuthenticationRequest;
import com.example.backend.Dtos.UserTokenState;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {
    private static final String URL_PREFIX = "/user";
    private static final String URL_LOGIN = "/auth/login";

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype());

    private MockMvc mockMvc;
    private static final Gson gson = new Gson();
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    @Transactional
    public void testAllNotEnabledUsers() throws Exception{
        logger.info("STRING: " + authorization());
        mockMvc.perform(get(URL_PREFIX + "/notEnabled").header("Authorization", authorization()))
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void testAllUsersExceptAdmins() throws Exception {
        mockMvc.perform(get(URL_PREFIX + "/allUsersExceptAdmins")
                .header("Authorization", authorization())).andExpect(status().isOk());

    }

    private String authorization() throws Exception {
        JwtAuthenticationRequest user = new JwtAuthenticationRequest();
        user.setUsername("qwe@gmail.com");
        user.setPassword("123");

        String result = this.mockMvc.perform(post(URL_LOGIN).contentType(contentType)
                .content(gson.toJson(user))).andReturn().getResponse().getContentAsString();

        return "Bearer " + gson.fromJson(result, UserTokenState.class).getAccessToken();
    }
}
