package com.security.securityDemoProject.controller;

import com.security.securityDemoProject.config.JwtUtils;
import com.security.securityDemoProject.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class OAuth2Controller {
    private final JwtUtils jwtUtils;
    private final UserService userService;

//    @GetMapping("/login")
//    public String login() {
//        return "login";
//    }

//    @GetMapping("/oauth2-success")
//    public String oauth2LoginSuccess(@AuthenticationPrincipal OAuth2User principal, Model model) {
//        String email = principal.getAttribute("email");
//        String name = principal.getAttribute("name");
//
//        // Xử lý đăng nhập OAuth2
//        userService.processOAuthPostLogin(email);
//
//        // Tạo JWT token
//        String token = jwtUtils.generateTokenFromUsername(email);
//
//        // Thêm thông tin vào model để hiển thị trên trang
//        model.addAttribute("name", name);
//        model.addAttribute("email", email);
//        model.addAttribute("token", token);
//        model.addAttribute("role", 0);
//
//        return "user-info";
//    }

//    @GetMapping("/loginFailure")
//    public String loginFailure() {
//        return "Login failed!";
//    }
}
