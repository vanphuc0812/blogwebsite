package com.example.blogwebsite.security.service;

import com.example.blogwebsite.common.exception.BWBusinessException;
import com.example.blogwebsite.security.oauth.user.UserPrinciple;
import com.example.blogwebsite.user.model.User;
import com.example.blogwebsite.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserPrincipleService implements UserDetailsService {
    private final UserRepository userRepository;

    public UserPrincipleService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserPrinciple loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(
                        () -> new BWBusinessException("User is not existed.")
                );

        return new UserPrinciple(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                user.getUsername(),
                Collections.emptyList()
        );
    }
}
