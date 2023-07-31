package com.codechallenge.salesforce.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
public class SalesforceDataProcessor {

    @Value("${salesforce.saveDataPoint}")
    private String saveDataPoint;

    public void pushDataToSalesforce(String authToken) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7");
        headers.set("Accept-Language", "en-US,en;q=0.9");
        headers.set("Cache-Control", "max-age=0");
        headers.set("Connection", "keep-alive");
        headers.set("Content-Type", "application/x-www-form-urlencoded");
        headers.set("Cookie", "BrowserId=CLO2EyyNEe6-MDsaRaOvxQ; BrowserId_sec=CLO2EyyNEe6-MDsaRaOvxQ; ..."); // Add the entire Cookie value here
        headers.set("Origin", "https://ibm-c1-dev-ed.develop.my.salesforce.com");
        headers.set("Referer", "https://ibm-c1-dev-ed.develop.my.salesforce.com/a02/e?retURL=%2Fa02%2Fo");
        headers.set("Sec-Fetch-Dest", "document");
        headers.set("Sec-Fetch-Mode", "navigate");
        headers.set("Sec-Fetch-Site", "same-origin");
        headers.set("Sec-Fetch-User", "?1");
        headers.set("Upgrade-Insecure-Requests", "1");
        headers.set("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.0.0 Safari/537.36");
        headers.set("sec-ch-ua", "\"Not/A)Brand\";v=\"99\", \"Google Chrome\";v=\"115\", \"Chromium\";v=\"115\"");
        headers.set("sec-ch-ua-mobile", "?0");
        headers.set("sec-ch-ua-platform", "\"Windows\"");


        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("_CONFIRMATIONTOKEN", "VmpFPSxNakF5TXkwd055MHpNVlF4TVRvME5Ub3hOaTQyTnpKYSxQY0NGeW1ZN2t6aHZjNGRScWI4TTZ2aVRRMFU5bHpqNllLU09vTmpOUTIwPSxOVGRtTUdZMg==");
        map.add("cancelURL", "/a02/o");
        map.add("retURL", "/a02/o");
        map.add("save_new_url", "/a02/e?retURL=%2Fa02%2Fo");
        map.add("Name", "aay123");
        map.add("00N5j00000Lej6o", "8");
        map.add("00N5j00000LekAa", "aayjain111206@");
        map.add("save", "Saving...");
        map.add("isSetupParam", "0");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
      try{
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(saveDataPoint, HttpMethod.POST, request, String.class);


        if (response.getStatusCode().is2xxSuccessful()) {
            System.out.println("Data pushed successfully to Salesforce.");
            System.out.println("Response Body: "
                    + response.getBody());
        } else {
            System.out.println("Error pushing data to Salesforce. Status Code: " + response.getStatusCodeValue());
            System.out.println("Error Response: " + response.getBody());
        }
    } catch (Exception e) {
        System.out.println("An error occurred while pushing data to Salesforce: " + e.getMessage());
        e.printStackTrace();
    }
    }
}
