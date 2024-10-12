package com.security.securityDemoProject.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.security.securityDemoProject.dto.ResponseDto;
import com.security.securityDemoProject.util.Constant;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtils jwtUtils;
    private final UserDetailsService userDetailsService;
    private final ObjectMapper objectMapper;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String jwt = getJwtFromRequest(request);

        // Kiểm tra nếu token có tồn tại và là hợp lệ
        if (jwt != null) {
            Constant.ErrorStatus errorTokenCheck = jwtUtils.validateJwtToken(jwt);

            if (errorTokenCheck.getCode() == Constant.ErrorStatus.SUCCESS.getCode()) {
                String username = jwtUtils.getUsernameFromJwtToken(jwt);
                List<String> roles = jwtUtils.getRolesFromJwtToken(jwt);

                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, getAuthorities(roles));
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Đặt SecurityContext của request hiện tại
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                // Trả về phản hồi lỗi và dừng xử lý
                sendErrorResponse(response, errorTokenCheck);
                return;  // Dừng chuỗi xử lý sau khi trả về phản hồi lỗi
            }
        }

        // Chỉ tiếp tục khi JWT hợp lệ
        filterChain.doFilter(request, response);
    }


    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    private Collection<? extends GrantedAuthority> getAuthorities(List<String> roles) {
        return roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    private void sendErrorResponse(HttpServletResponse response, Constant.ErrorStatus errorStatus) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        ResponseDto<Object> errorResponse = ResponseDto.error(errorStatus, null);
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}
