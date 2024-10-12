package com.security.securityDemoProject.controller;

import com.security.securityDemoProject.config.JwtUtils;
import com.security.securityDemoProject.dto.ResponseDto;
import com.security.securityDemoProject.entity.RoleEntity;
import com.security.securityDemoProject.entity.RoleName;
import com.security.securityDemoProject.entity.UserEntity;
import com.security.securityDemoProject.payload.LoginRequest;
import com.security.securityDemoProject.payload.SignupRequest;
import com.security.securityDemoProject.reponsitory.RoleRepository;
import com.security.securityDemoProject.reponsitory.UserRepository;
import com.security.securityDemoProject.service.UserService;
import com.security.securityDemoProject.util.Constant;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
@Tag(name = "Xác thực", description = "API xác thực")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final UserService userService;
    private final JwtUtils jwtUtils;

    @PostMapping("/login")
    @Operation(summary = "Đăng nhập", description = "Đăng nhập với tên đăng nhập và mật khẩu")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        return userService.authenticateUser(loginRequest);
    }


    @PostMapping("/register")
    @Operation(summary = "Đăng ký", description = "Đăng ký tài khoản mới")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {
        return userService.registerUser(signUpRequest);
    }


    @DeleteMapping("/user/delete/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Xóa người dùng", description = "Xóa người dùng theo tên đăng nhập")
    public ResponseEntity<?> deleteUser(@PathVariable long userId) {
        return userService.deleteUser(userId);
    }

    @GetMapping("/me")
    @Operation(summary = "Lấy thông tin người dùng hiện tại", description = "Lấy thông tin của người dùng đã đăng nhập")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lấy thông tin thành công"),
            @ApiResponse(responseCode = "401", description = "Chưa đăng nhập")
    })
    public ResponseEntity<?> getCurrentUser() {
       return userService.getCurrentUser();
    }

    @DeleteMapping("/user/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Lấy người dùng", description = "Lấy người dùng theo id")
    public ResponseEntity<?> getUser(@PathVariable long userId) {
        return userService.getUserById(userId);
    }





    /*

    @PostMapping("/register/admin")
@PreAuthorize("hasRole('ADMIN')")  // Chỉ ADMIN hiện tại mới có thể tạo ADMIN mới
public ResponseEntity<?> registerAdmin(@RequestBody SignupRequest signUpRequest) {
    if (userRepository.existsByUsername(signUpRequest.getUsername())) {
        return ResponseEntity.badRequest().body("{\"message\": \"Tên đăng nhập đã tồn tại\"}");
    }

    User user = new User(signUpRequest.getUsername(),
            passwordEncoder.encode(signUpRequest.getPassword()),
            "ADMIN");

    userRepository.save(user);

    return ResponseEntity.ok().body("{\"message\": \"Đăng ký ADMIN thành công\"}");
}


@PostMapping("/register")
public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {
    if (userRepository.existsByUsername(signUpRequest.getUsername())) {
        return ResponseEntity.badRequest().body("{\"message\": \"Tên đăng nhập đã tồn tại\"}");
    }

    User user = new User(signUpRequest.getUsername(),
            passwordEncoder.encode(signUpRequest.getPassword()),
            "USER");  // Gán vai trò mặc định là USER

    userRepository.save(user);

    return ResponseEntity.ok().body("{\"message\": \"Đăng ký thành công\"}");
}



     @DeleteMapping("/user/{username}")
@Operation(summary = "Xóa người dùng", description = "Xóa người dùng theo tên đăng nhập (chỉ ADMIN)")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Xóa người dùng thành công"),
        @ApiResponse(responseCode = "404", description = "Không tìm thấy người dùng"),
        @ApiResponse(responseCode = "403", description = "Không có quyền xóa người dùng")
})
@PreAuthorize("hasRole('ADMIN')")  // Thêm annotation này
public ResponseEntity<?> deleteUser(@PathVariable String username) {
    User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));

    userRepository.delete(user);
    return ResponseEntity.ok().body("{\"message\": \"Xóa người dùng thành công\"}");
}
     */
}