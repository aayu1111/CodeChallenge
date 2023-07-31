package com.codechallenge.service;


import com.codechallenge.entity.UserInfo;
import com.codechallenge.repository.UserInfoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
public class UserInfoImplTest {

    @InjectMocks
    UserInfoImpl userInfoImpl;

    @Mock
    UserInfoRepository userInfoRepository;

    @Mock
    PasswordEncoder passwordEncoder;


    @Test
    public void addUserInfoTest(){
        Optional<UserInfo> optional= Optional.ofNullable(UserInfo.builder().name("test").password("tester").build());
        Mockito.when(userInfoRepository.findByName("test")).thenReturn(optional);
        assertEquals("Data Saved",userInfoImpl.addUserInfo(UserInfo.builder().name("test").password("tester").build()));

    }
}
