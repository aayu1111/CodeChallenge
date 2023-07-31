package com.codechallenge.controller;

import com.codechallenge.entity.Role;
import com.codechallenge.entity.SignInRequest;
import com.codechallenge.entity.UserInfo;
import com.codechallenge.repository.UserInfoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class SignInControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    SignInController signInController;

    @Autowired
    PasswordEncoder encoder;

    @Test
    @WithMockUser(username = "test",password = "testValue")
    public void testAuthenticateAndGetToken_ValidUser() throws Exception {
        SignInRequest signInRequest = new SignInRequest();
        signInRequest.setUsername("test");
        signInRequest.setPassword("testValue");
        userInfoRepository.save(UserInfo.builder().name("test").password(encoder.encode("testValue")).role(Role.USER).email("aaj").build());

        mvc.perform(post("/signIn")
                        .content(asJsonString(signInRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    private String asJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
