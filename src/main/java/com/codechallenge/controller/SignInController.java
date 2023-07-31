package com.codechallenge.controller;

import com.codechallenge.entity.SignInRequest;
import com.codechallenge.entity.UserInfo;
import com.codechallenge.service.JwtServiceImpl;
import com.codechallenge.service.UserInfoImpl;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SignInController {

    @Autowired
    private JwtServiceImpl jwtServiceImpl;

    @Autowired
    UserInfoImpl userInfoImpl;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Operation(summary = "Api endpoint for user to sign in")
    @PostMapping("/signIn")
    public String authenticateAndGetToken(@RequestBody SignInRequest signInRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getUsername(), signInRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtServiceImpl.generateToken(signInRequest.getUsername());
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }
    }

    @Operation(summary = "Api endpoint to add UserInfo")
    @PostMapping(value = "/userInfo")
    public ResponseEntity<String> addCustomer(@RequestBody UserInfo userInfo) {
        String response = userInfoImpl.addUserInfo(userInfo);
        return new ResponseEntity < >(response, HttpStatus.CREATED);
    }
}
