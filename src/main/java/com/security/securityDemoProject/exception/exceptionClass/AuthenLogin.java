package com.security.securityDemoProject.exception.exceptionClass;

import org.springframework.security.core.AuthenticationException;

public class AuthenLogin extends RuntimeException {
    public AuthenLogin(String msg) {
        super(msg);
    }
}
