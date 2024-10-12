package com.security.securityDemoProject.exception;

import com.security.securityDemoProject.dto.ResponseDto;
import com.security.securityDemoProject.exception.exceptionClass.AuthenLogin;
import com.security.securityDemoProject.util.Constant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.management.relation.RoleNotFoundException;
import javax.naming.AuthenticationException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<String> handleRoleNotFoundException(RoleNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    // Xử lý ngoại lệ xác thực
    @ExceptionHandler(AuthenLogin.class)
    public ResponseEntity<ResponseDto> handleAuthenticationException(AuthenLogin ex) {
        ResponseDto responseDto = ResponseDto.error(Constant.ErrorStatus.LOGIN_FAILED, "Invalid username or password");
        return new ResponseEntity<>(responseDto, HttpStatus.UNAUTHORIZED);
    }

    // Xử lý các lỗi liên quan đến SQL
    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ResponseDto> handleSQLException(SQLException ex) {
        ResponseDto responseDto = ResponseDto.error(Constant.ErrorStatus.ERROR_500, "Database error: " + ex.getMessage());
        return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Xử lý ngoại lệ chung
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDto> handleGeneralException(Exception ex) {
        ResponseDto responseDto = ResponseDto.error(Constant.ErrorStatus.ERROR_500, "Internal server error");
        responseDto.setMessage(ex.getMessage());
        return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
