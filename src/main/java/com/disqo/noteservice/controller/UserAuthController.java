package com.disqo.noteservice.controller;

import com.disqo.noteservice.service.UserAuthService;
import com.disqo.noteservice.dto.LogInRequest;
import com.disqo.noteservice.dto.LogInResponse;
import com.disqo.noteservice.jwt.JwtUtils;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@CommonsLog
public class UserAuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserAuthService userAuthService;

    @PostMapping(value = "/auth")
    public ResponseEntity<LogInResponse> createAuthToken(@RequestBody LogInRequest logInRequest) {
        authenticate(logInRequest.getEmail(), logInRequest.getPassword());
        final UserDetails userDetails = userAuthService
                .loadUserByUsername(logInRequest.getEmail());

        final String token = jwtUtils.generateToken(userDetails);
        return ResponseEntity.ok(new LogInResponse(token));
    }

    private void authenticate(String email, String password) throws DisabledException, BadCredentialsException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (DisabledException e) {
            log.error("USER_DISABLED" + e);
            throw new DisabledException("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            log.error("INVALID_CREDENTIALS" + e);
            throw new BadCredentialsException("INVALID_CREDENTIALS", e);
        }
    }
}
