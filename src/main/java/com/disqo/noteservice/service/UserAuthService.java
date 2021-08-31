package com.disqo.noteservice.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserAuthService extends UserDetailsService {

    UserDetails loadUserByUsername(String email);
}
