package com.codechallenge.service;

import com.codechallenge.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl {

    @Autowired
    UserInfoRepository userRepository;
            public UserDetailsService userDetailsService() {
                return new UserDetailsService() {
                    @Override
                    public UserDetails loadUserByUsername(String username) {
                        return userRepository.findByName(username)
                                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
                    }
                };
            }
        }
