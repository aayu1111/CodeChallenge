package com.codechallenge.service;

import com.codechallenge.entity.UserInfo;
import com.codechallenge.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserInfoImpl {

    @Autowired
    UserInfoRepository userInfoRepository;

    @Autowired
    PasswordEncoder passwordEncoder;


    public String addUserInfo(UserInfo userInfo) {
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        UserInfo userInfoResponse= userInfoRepository.save(userInfo);
        return "Data Saved";
    }
}
