package com.security.securityDemoProject.service;

import com.security.securityDemoProject.dto.ResponseDto;
import com.security.securityDemoProject.dto.UserDto;
import com.security.securityDemoProject.payload.LoginRequest;
import com.security.securityDemoProject.payload.SignupRequest;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<?> registerUser(SignupRequest signUpRequest);
    ResponseEntity<?> authenticateUser(LoginRequest loginRequest);
    ResponseEntity<?> deleteUser(long userId);
    ResponseEntity<?> getCurrentUser();
    ResponseEntity<?> getUserById(long userId);
    //void processOAuthPostLogin(String email);
}

