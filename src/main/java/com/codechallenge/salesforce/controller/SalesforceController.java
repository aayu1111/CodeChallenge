package com.codechallenge.salesforce.controller;

import com.codechallenge.salesforce.config.SalesforceUtil;
import com.codechallenge.salesforce.service.SalesforceDataProcessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class SalesforceController {

    @Autowired
    private SalesforceUtil salesforceUtil;

    @Autowired
    SalesforceDataProcessor salesforceDataProcessor;

    @GetMapping("/salesforce")
    public RedirectView salesforceCallback(@RequestParam("code") String authorizationCode) throws JsonProcessingException {
        String accessToken = salesforceUtil.fetchAccessToken(authorizationCode);
        System.out.println(accessToken);

        salesforceDataProcessor.pushDataToSalesforce(accessToken);
        return new RedirectView("/success");
    }


    @GetMapping("/initiate-auth")
    public RedirectView initiateAuth() {
        String authorizationUrl = salesforceUtil.getAuthorizationUrl();
        return new RedirectView(authorizationUrl);
    }
}
