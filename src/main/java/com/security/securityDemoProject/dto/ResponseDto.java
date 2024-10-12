package com.security.securityDemoProject.dto;

import com.security.securityDemoProject.util.Constant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ResponseDto<T> {
    private LocalDateTime timestamp; // Thời gian phản hồi
    private String message;// Thông điệp phản hồi
    private int errorCode;
    private String status;           // Trạng thái ("success" hoặc "error")
    private T data;

    public static <T> ResponseDto<T> success(Constant.ErrorStatus errorStatus, T data) {
        return new ResponseDto<>(LocalDateTime.now(), errorStatus.getMsg(), errorStatus.getCode(), "success", data);
    }

    public static <T> ResponseDto<T> error(Constant.ErrorStatus errorStatus, T data) {
        return new ResponseDto<>(LocalDateTime.now(), errorStatus.getMsg(), errorStatus.getCode(), "error", data);
    }

    public static <T> ResponseDto<T> errorException(String message, int errorCode, T data) {
        return new ResponseDto<>(LocalDateTime.now(), message, errorCode, "error", data);
    }
}
