package com.codechallenge.controller;

import com.codechallenge.entity.Customer;
import com.codechallenge.repository.CustomerRepository;
import com.codechallenge.service.JwtServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    JwtServiceImpl jwtService;


    @Test
    @WithMockUser(roles = "USER",username = "test",password = "tester")
    public void testAddCustomerWithUserRoleValidJWT() throws Exception {
        Customer customer=Customer.builder().id("1")
                    .email("test@gmail.com").name("test").phone("testdata").build();

        String validToken=jwtService.generateToken("test");
        mockMvc.perform(post("/api/customers")
                        .header("Authorization", "Bearer " + validToken)
                        .content(asJsonString(customer))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testAddCustomerWithAdminRoleInvalidJWT() throws Exception {
        Customer customer=Customer.builder().id("1")
                .email("test@gmail.com").name("test").phone("testdata").build();

        String mockFalseToken="eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhYXl1c2hpaWkiLCJpYXQiOjE2OTA3MTI5NzAsImV4cCI6MTY5MDcxNDc3MH0.jmd6El1ao7a505BTGcqNlEWxgDOrqj8PyrQyURqUipQ";
        mockMvc.perform(post("/api/customers")
                        .header("Authorization", "Bearer " + mockFalseToken)
                        .content(asJsonString(customer))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());


    }

    @Test
    public void testAddCustomerWithExpiredToken() throws Exception {
        Customer customer=Customer.builder().id("1")
                .email("test@gmail.com").name("test").phone("testdata").build();
        String expiredToken = generateExpiredToken("test");

        mockMvc.perform(post("/customers")
                        .header("Authorization", "Bearer " + expiredToken) // Set the expired token in the Authorization header
                        .content(asJsonString(customer))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    private String generateExpiredToken(String userName) {
        final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";

        byte[] keyBytes= Decoders.BASE64.decode(SECRET);
        Date expirationDate = new Date(System.currentTimeMillis() - 1000); // Set the expiration date to a time in the past
        Claims claims = Jwts.claims().setSubject(userName);

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(expirationDate)
                .signWith(Keys.hmacShaKeyFor(keyBytes))
                .compact();
    }


    @Test
    public void testAddCustomerWithoutAuthentication() throws Exception {
        Customer customer=Customer.builder().id("1")
                .email("test@gmail.com").name("test").phone("testdata").build();

            mockMvc.perform(post("/api/customers")
                            .content(asJsonString(customer))
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isForbidden());
        }

        private String asJsonString(Object obj) {
            try {
                return new ObjectMapper().writeValueAsString(obj);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

