package com.security.securityDemoProject.util;

public class Constant {
    public enum Role {
        USER,
        ADMIN
    }

    public enum Provider {
        LOCAL, GOOGLE, FACEBOOK, GITHUB
    }

    public enum ErrorStatus {
        // Declare constants first
        ERROR_404(404, "Not Found"),


        // System Error 1 - 100

        // Role, User Error 101 - 200
        REGISTER_SUCCESS(101, "Đăng ký thành công"),
        REGISTER_AVAILABLEUSERNAME(102, "Tên đăng nhập đã tồn tại"),
        LOGIN_SUCCESS(103, "Đăng nhập thành công"),
        LOGIN_FAILED(104, "Sai tên đăng nhập hoặc mật khẩu"),
        ROLE_NOTFOUND(105, "Vai trò không tồn tại"),

        SUCCESS_200(200, "Thành công"),
        ERROR_500(500, "Internal Server Error"),

        // jwt status
        SUCCESS(0, "Thành công"),
        INVALID_SIGNATURE(1001, "Chữ ký JWT không hợp lệ"),
        MALFORMED(1002, "JWT không đúng định dạng"),
        EXPIRED(1003, "JWT đã hết hạn"),
        UNSUPPORTED(1004, "JWT không được hỗ trợ"),
        ILLEGAL_ARGUMENT(1005, "JWT claims rỗng");
        // Fields
        private final int code;
        private final String msg;

        // Constructor
        private ErrorStatus(final int code, final String msg) {
            this.code = code;
            this.msg = msg;
        }

        // Getter methods (optional)
        public int getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }
    }


}
