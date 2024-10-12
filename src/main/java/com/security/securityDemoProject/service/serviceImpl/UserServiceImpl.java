package com.security.securityDemoProject.service.serviceImpl;

import com.security.securityDemoProject.config.JwtUtils;
import com.security.securityDemoProject.controller.AuthController;
import com.security.securityDemoProject.dto.ResponseDto;
import com.security.securityDemoProject.dto.UserDto;
import com.security.securityDemoProject.entity.UserEntity;
import com.security.securityDemoProject.exception.exceptionClass.AuthenLogin;
import com.security.securityDemoProject.mapper.UserMapper;
import com.security.securityDemoProject.payload.LoginRequest;
import com.security.securityDemoProject.payload.SignupRequest;
import com.security.securityDemoProject.reponsitory.UserRepository;
import com.security.securityDemoProject.service.RoleService;
import com.security.securityDemoProject.service.UserService;
import com.security.securityDemoProject.util.Constant;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public ResponseEntity<?> authenticateUser(LoginRequest loginRequest) throws AuthenticationException {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );
        } catch (AuthenticationException e) {
            throw new AuthenLogin(Constant.ErrorStatus.LOGIN_FAILED.getMsg());
        }

        // Lưu thông tin xác thực vào SecurityContext
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Tạo JWT token
        String jwt = jwtUtils.generateJwtToken(authentication);
        logger.info("authenticateUser: {}", loginRequest.getUsername());

        // Tạo phản hồi trả về token
        return ResponseEntity.ok(ResponseDto.success(Constant.ErrorStatus.SUCCESS_200, jwt));
    }


    @Override
    public ResponseEntity<?> registerUser(SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(ResponseDto.error(Constant.ErrorStatus.REGISTER_AVAILABLEUSERNAME, null));
        }

        // Tạo người dùng mới
        UserEntity user = new UserEntity();
        user.setUsername(signUpRequest.getUsername());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

        // Xử lý và gán vai trò cho người dùng
        String strRole = String.valueOf(signUpRequest.getRole());
        user.setRoles(roleService.getRoleUserRegister(strRole));
        // Lưu người dùng vào cơ sở dữ liệu
        logger.info("registerUser: {}, {}", signUpRequest.getUsername(), signUpRequest.getPassword());
        userRepository.save(user);
        return ResponseEntity.badRequest().body(ResponseDto.error(Constant.ErrorStatus.SUCCESS_200, null));
    }

    public ResponseEntity<?> deleteUser(long userId){
        Optional<UserEntity> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        userRepository.delete(user.get());
        logger.info("deleteUser: {}, {}",user.get().getId(), user.get().getUsername());
        return ResponseEntity.ok(ResponseDto.success(Constant.ErrorStatus.SUCCESS_200, null));
    }

    @Override
    public ResponseEntity<?> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        Optional<UserEntity> userOptional = userRepository.findByUsernameWithRoles(currentUsername);

        if (userOptional.isEmpty()) {
            return ResponseEntity.badRequest().body(ResponseDto.error(Constant.ErrorStatus.ERROR_404, null));
        }
        UserEntity user = userOptional.get();
        logger.info("user: {}, {}, {}", String.valueOf(user.getRoles()), user.getId(), user.getUsername());
        // Chuyển đổi UserEntity sang UserDto
        UserDto userDto = UserMapper.INSTANCE.userEntityToUserDto(user);

        return ResponseEntity.ok(userDto);
    }

    @Override
    public ResponseEntity<?> getUserById(long userId) {
        Optional<UserEntity> user = userRepository.findById(userId);

        if (user.isEmpty()) {
            return ResponseEntity.badRequest().body(ResponseDto.error(Constant.ErrorStatus.ERROR_404, null));
        }

        // Chuyển đổi UserEntity sang UserDto
        UserDto userDto = UserMapper.INSTANCE.userEntityToUserDto(user.get());

        return ResponseEntity.ok(ResponseDto.success(Constant.ErrorStatus.SUCCESS_200, userDto));
    }

//    public void processOAuthPostLogin(String email) {
//        Optional<UserEntity> existUser = userRepository.findByEmail(email);
//
//        if (existUser.isPresent()) {
//            UserEntity newUser = new UserEntity();
//            newUser.setEmail(email);
//            newUser.setUsername(email);  // Sử dụng email làm username
//            newUser.setProvider(Constant.Provider.GOOGLE);
//            newUser.setEnabled(true);
//
//            userRepository.save(newUser);
//        }
//    }

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
}
