package com.codechallenge.salesforce.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class SalesforceUtil {

    @Value("${salesforce.oauth.client-id}")
    private String clientId;

    @Value("${salesforce.oauth.client-secret}")
    private String clientSecret;

    @Value("${salesforce.oauth.redirect-uri}")
    private String redirectUri;

    private final RestTemplate restTemplate;

    @Autowired
    public SalesforceUtil(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public String getAuthorizationUrl() {
        return "https://login.salesforce.com/services/oauth2/authorize" +
                "?response_type=code" +
                "&client_id=" + clientId +
                "&redirect_uri=" + redirectUri;
    }

    public String fetchAccessToken(String authorizationCode) throws JsonProcessingException {
        String tokenEndpointUrl = "https://login.salesforce.com/services/oauth2/token";

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);
        body.add("redirect_uri", redirectUri);
        body.add("code", authorizationCode);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(tokenEndpointUrl, entity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> jsonMap = objectMapper.readValue(response.getBody(), Map.class);
            String accessToken = (String) jsonMap.get("access_token");

            return accessToken;
        } else {
            return null;
        }
    }
}
