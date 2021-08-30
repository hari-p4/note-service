package com.disqo.noteservice.service;

import com.disqo.noteservice.domain.UserEntity;
import com.disqo.noteservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;

@Service
public class UserAuthService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(email);
        if (ObjectUtils.isEmpty(user)) {
            throw new UsernameNotFoundException("No user found with email : " + email);
        }
        return new User(user.getEmail(), user.getPassword(), new ArrayList<>());
    }
}
